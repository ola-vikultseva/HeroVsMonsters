package creatures

import game.Dice
import kotlin.random.Random

class Player(
    name: String,
    attack: Int,
    defense: Int,
    maxHealth: Int,
    damage: IntRange,
    random: Random,
    dice: Dice
) : Creature(name, attack, defense, maxHealth, damage, random, dice) {

    var healCount = MAX_HEALS
        private set

    override fun takeTurn(enemies: List<Creature>) {
        if (currentHealth < maxHealth / 2 && healCount > 0) {
            heal()
            healCount--
            println("Remaining heal count: $healCount.")
        }
        val target = chooseTarget(enemies) ?: return
        attackTarget(target)
    }

    private fun chooseTarget(enemies: List<Creature>): Creature? {
        val aliveEnemies = enemies.filter { it.isAlive }
        return aliveEnemies.minByOrNull { it.currentHealth }
    }

    private fun heal() {
        val healAmount = (maxHealth * HEAL_PERCENT).toInt().coerceAtLeast(1)
        restoreHealth(healAmount)
        println("$name healed for $healAmount HP. Current HP: $currentHealth")
    }

    private fun restoreHealth(amount: Int) {
        currentHealth = (currentHealth + amount).coerceAtMost(maxHealth)
    }

    companion object {
        private const val MAX_HEALS = 4
        private const val HEAL_PERCENT = 0.3
    }
}