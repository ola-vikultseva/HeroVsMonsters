package game

import kotlin.random.Random

class Dice(
    val sides: Int,
    val random: Random
) {
    fun roll(): Int = random.nextInt(1, sides + 1)
}