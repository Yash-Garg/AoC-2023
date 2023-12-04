@file:Suppress("Unused")

data class Game(
    val id: Int,
    val cubes: List<Map<String, Int>>,
)

fun main() {
    fun solve(
        input: List<String>,
        solve: (List<Game>) -> Int,
    ): Int {
        val games = input.map { line ->
            val (gid, value) = line.split(": ")
            Game(
                id = gid.removePrefix("Game ").toInt(),
                cubes = value.split("; ").map { set ->
                    set.split(", ")
                        .associate { amountWithColor ->
                            val (amount, color) = amountWithColor.split(" ")
                            color to amount.toInt()
                        }
                },
            )
        }

        return solve(games)
    }

    fun part1(input: List<String>): Int = solve(input) { games ->
        val cubeTypes = mapOf("red" to 12, "green" to 13, "blue" to 14)
        games
            .filter {
                it.cubes.all { round ->
                    round.all { (color, count) -> count <= cubeTypes.getValue(color) }
                }
            }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int = solve(input) { games ->
        games.sumOf { game ->
            buildMap {
                game.cubes.forEach { sets ->
                    sets.forEach { (color, amount) ->
                        put(color, maxOf(getOrDefault(color, amount), amount))
                    }
                }
            }.values.reduce(Int::times)
        }
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
