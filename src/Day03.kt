import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

data class NumXY(
    val value: Int,
    val startX: Int,
    val endX: Int,
    val y: Int,
) {
    val points: PersistentList<Point>
        get() = (startX..endX).map { Point(x = it, y = y) }.toPersistentList()

    companion object {
        fun parse(lines: List<String>, block: (List<NumXY>) -> Int): Int {
            val regex = "\\d+".toRegex()
            val numbers = lines.flatMapIndexed { lineNum, line ->
                regex.findAll(line).map { match ->
                    NumXY(
                        value = match.value.toInt(),
                        startX = match.range.first,
                        endX = match.range.last,
                        y = lineNum,
                    )
                }
            }

            return block(numbers)
        }
    }
}

data class Point(
    val x: Int,
    val y: Int,
) {
    override fun toString() = "($x, $y)"

    fun adjacent(takeDiag: Boolean = false): PersistentList<Point> {
        val horizontal = persistentListOf(
            Point(x = x, y = y.dec()),
            Point(x = x, y = y.inc()),
            Point(x = x.dec(), y = y),
            Point(x = x.inc(), y = y),
        )

        val diagonal = persistentListOf(
            Point(x = x.dec(), y = y.dec()),
            Point(x = x.dec(), y = y.inc()),
            Point(x = x.inc(), y = y.dec()),
            Point(x = x.inc(), y = y.inc()),
        )

        return if (takeDiag) horizontal.addAll(diagonal) else horizontal
    }
}

fun main() {
    fun part1(input: List<String>) = NumXY.parse(input) { numbers ->
        numbers.filterNot { num ->
            num.points.all { pt ->
                pt.adjacent(true).all {
                    val ch = input.getOrNull(it.y)?.getOrNull(it.x)
                    ch == null || ch.isDigit() || ch == '.'
                }
            }
        }.sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        var ratios = 0

        return NumXY.parse(input) { numbers ->
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, ch ->
                    if (ch == '*') {
                        val adjPoints = Point(x, y).adjacent(true)
                        val adjNumbers = numbers.filter { n ->
                            n.points.any { it in adjPoints }
                        }.map { it.value }

                        if (adjNumbers.size == 2) {
                            ratios += adjNumbers[0].times(adjNumbers[1])
                        }
                    }
                }
            }

            ratios
        }
    }

    readInput("Day03_test").also {
        check(part1(it) == 4361)
        check(part2(it) == 467835)
    }

    readInput("Day03").also {
        part1(it).println()
        part2(it).println()
    }
}
