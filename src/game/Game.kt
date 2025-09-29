package game

import creatures.Creature
import kotlin.random.Random

class Game(
    private val player: Creature,
    private val monsters: List<Creature>,
    private val random: Random
) {

    fun launch() {
        println("=== Battle Start ===")
        var round = 1
        val participants = (monsters + player).toMutableList()
        while (player.isAlive && monsters.any { it.isAlive }) {
            println("--- Round $round ---")
            participants.shuffle(random)
            var turn = 1
            for (creature in participants) {
                if (!creature.isAlive) continue
                val enemies = if (creature == player) {
                    monsters.filter { it.isAlive }
                } else {
                    listOf(player).filter { it.isAlive }
                }
                if (enemies.isEmpty()) break
                println("[Round $round | Turn $turn] ${creature.name}'s turn")
                creature.takeTurn(enemies)
                logHealthStatus()
                turn++
            }
            round++
        }
        println("=== Battle End ===")
        announceWinner()
    }

    private fun logHealthStatus() {
        val playerStatus = "${player.name}: ${player.currentHealth}/${player.maxHealth} HP"
        val monstersStatus = monsters.joinToString(" | ") { monster ->
            "${monster.name}: ${monster.currentHealth}/${monster.maxHealth} HP"
        }
        println("$playerStatus || $monstersStatus")
    }

    private fun announceWinner() {
        val winner = if (player.isAlive) player.name else "Monsters"
        println("Winner: $winner")
    }
}
