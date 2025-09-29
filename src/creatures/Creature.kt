package creatures

import game.Dice
import kotlin.random.Random

abstract class Creature(
    val name: String,
    val attack: Int,
    val defense: Int,
    val maxHealth: Int,
    val damage: IntRange,
    val random: Random,
    val dice: Dice
) {

    var currentHealth = maxHealth
        protected set

    val isAlive: Boolean
        get() = currentHealth > 0

    init {
        require(name.isNotBlank()) { "Name must not be blank." }
        require(attack in 1..30) { "Attack must be between 1 and 30." }
        require(defense in 1..30) { "Defense must be between 1 and 30." }
        require(maxHealth >= 1) { "Max health must be at least 1." }
        require(damage.first >= 1) { "Min damage must be at least 1." }
        require(damage.last > damage.first) { "Max damage must be greater than min damage." }
    }

    abstract fun takeTurn(enemies: List<Creature>)

    protected fun attackTarget(enemy: Creature) {
        require(enemy !== this) { "$name cannot attack itself." }
        require(this.isAlive) { "$name is dead and cannot attack." }
        require(enemy.isAlive) { "${enemy.name} is already dead. No need to attack." }

        if (calculateHitSuccess(enemy.defense)) {
            val damage = random.nextInt(damage.first, damage.last + 1)
            enemy.applyDamage(damage)
            println("$name dealt $damage damage to ${enemy.name}.")
        } else {
            println("The attack had no effect on ${enemy.name}")
        }
    }

    private fun calculateHitSuccess(enemyDefense: Int): Boolean {
        val attackModifier = attack - enemyDefense + 1
        val attemptCount = maxOf(1, attackModifier)
        println("Attack modifier: $attackModifier, attempts: $attemptCount")
        val rolls = mutableListOf<Int>()
        val success = (1..attemptCount).any {
            val roll = dice.roll()
            rolls += roll
            roll >= HIT_THRESHOLD
        }
        println("$name rolls: ${rolls.joinToString(", ")} -> " + if (success) "Hit!" else "Miss.")
        return success
    }

    private fun applyDamage(damage: Int) {
        currentHealth = (currentHealth - damage).coerceAtLeast(0)
        if (isAlive) {
            println("$name took $damage damage. HP: $currentHealth/$maxHealth.")
        } else {
            println("$name has fallen in battle!")
        }
    }

    companion object {
        private const val HIT_THRESHOLD = 5
    }
}