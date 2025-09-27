import factory.EntityFactory
import game.Dice
import game.Game
import kotlin.random.Random

const val MONSTER_COUNT = 3
const val DICE_SIDES = 6

fun initGame(seed: Long = System.currentTimeMillis()): Game {
    val random = Random(seed)
    val dice = Dice(DICE_SIDES, random)
    val player = EntityFactory.generatePlayer(random, dice)
    val monsters = List(MONSTER_COUNT) { EntityFactory.generateMonster(random, dice) }
    println("Game initialized. Random seed: $seed")
    return Game(player, monsters, random)
}

fun main() {
    val game = initGame()
    game.launch()
}