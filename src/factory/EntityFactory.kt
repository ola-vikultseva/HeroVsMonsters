package factory

import creatures.Monster
import creatures.Player
import game.Dice
import kotlin.random.Random

object EntityFactory {

    private val monsterNames = listOf("Fierce Goblin", "Savage Orc", "Ancient Dragon", "Cunning Witch", "Dark Werewolf", "Wild Centaur")

    private const val ATTACK_MIN = 1
    private const val ATTACK_MAX = 30
    private const val DEFENSE_MIN = 1
    private const val DEFENSE_MAX = 30
    private const val HP_MIN = 1
    private const val HP_MAX = 100
    private const val DAMAGE_MIN = 1
    private const val DAMAGE_MAX = 30
    private const val DAMAGE_SPREAD = 10

    fun generatePlayer(random: Random, dice: Dice): Player {
        val player = Player(
            name = "Hero",
            attack = random.nextInt(ATTACK_MIN, ATTACK_MAX + 1),
            defense = random.nextInt(DEFENSE_MIN, DEFENSE_MAX + 1),
            maxHealth = random.nextInt(HP_MIN, HP_MAX + 1),
            damage = generateDamageRange(random),
            random = random,
            dice = dice
        )
        println("Generated player: ${player.name} | ATK=${player.attack}, DEF=${player.defense}, HP=${player.currentHealth}/${player.maxHealth}, DMG=${player.damage}")
        return player
    }

    fun generateMonster(random: Random, dice: Dice): Monster {
        val monster = Monster(
            name = monsterNames.random(random),
            attack = random.nextInt(ATTACK_MIN, ATTACK_MAX + 1),
            defense = random.nextInt(DEFENSE_MIN, DEFENSE_MAX + 1),
            maxHealth = random.nextInt(HP_MIN, HP_MAX + 1),
            damage = generateDamageRange(random),
            random = random,
            dice = dice
        )
        println("Generated monster: ${monster.name} | ATK=${monster.attack}, DEF=${monster.defense}, HP=${monster.currentHealth}/${monster.maxHealth}, DMG=${monster.damage}")
        return monster
    }

    private fun generateDamageRange(random: Random): IntRange {
        val base = random.nextInt(DAMAGE_MIN, DAMAGE_MAX - DAMAGE_SPREAD + 1)
        return base..(base + DAMAGE_SPREAD)
    }
}