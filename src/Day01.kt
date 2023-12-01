enum class Digits {
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;

    override fun toString() = this.name.lowercase()
}

fun main() {
    fun part1(input: List<String>) = input.sumOf {
        val nums = it.toCharArray().filter { ch -> ch.isDigit() }
        "${nums.first()}${nums.last()}".toInt()
    }

    fun part2(input: List<String>) = input.sumOf { line ->
        val numMap = mutableMapOf<Int, Int>()

        Digits.entries.forEach { digit ->
            var index = line.indexOf(digit.toString())
            while (index > -1) {
                numMap[index] = digit.ordinal.inc()
                index = line.indexOf(digit.toString(), index.inc())
            }
        }

        line.toCharArray().mapIndexed { index, c ->
            if (c.isDigit()) numMap[index] = c.digitToInt()
        }

        val nums = numMap.toSortedMap().values
        "${nums.first()}${nums.last()}".toInt()
    }

    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 142)
    //check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
