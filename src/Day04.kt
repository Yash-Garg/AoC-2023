fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    readInput("Day04_test").also {
        check(part1(it) == 13)
//        check(part2(it) == 467835)
    }

//    readInput("Day04").also {
//        part1(it).println()
//        part2(it).println()
//    }
}
