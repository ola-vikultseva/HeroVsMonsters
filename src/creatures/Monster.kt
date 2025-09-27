package creatures

import game.Dice
import kotlin.random.Random

class Monster(
    name: String,
    attack: Int,
    defense: Int,
    maxHealth: Int,
    damage: IntRange,
    random: Random,
    dice: Dice
) : Creature(name, attack, defense, maxHealth, damage, random, dice) {

    override fun takeTurn(enemies: List<Creature>) {
        val target = enemies.firstOrNull { it.isAlive } ?: return
        attackTarget(target)
    }
}