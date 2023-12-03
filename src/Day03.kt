fun main() {
    fun isAdjacentToSymbol(x: Int, y: Int, input: List<String>, symbols: Set<Char>): Boolean {
        for (dx in -1..1) {
            for (dy in -1..1) {
                if (dx == 0 && dy == 0) continue
                val nx = x + dx
                val ny = y + dy
                if (ny in input.indices && nx in input[ny].indices && input[ny][nx] in symbols) {
                    return true
                }
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        val symbols = setOf('*', '#', '+', '$', '-', '=', '/', '@', '%', '&')
        var sum = 0

        for (y in input.indices) {
            var x = 0
            while (x < input[y].length) {
                if (input[y][x].isDigit()) {
                    var numberLength = 0
                    while (x + numberLength < input[y].length && input[y][x + numberLength].isDigit()) {
                        numberLength++
                    }

                    val number = input[y].substring(x, x + numberLength).toInt()
                    for (i in 0 until numberLength) {
                        if (isAdjacentToSymbol(x + i, y, input, symbols)) {
                            sum += number
                            break
                        }
                    }
                    x += numberLength
                } else {
                    x++
                }
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Test with the example input
    val testInput = listOf(
        "467..114..",
        "...*......",
        "..35..633.",
        "......#...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...$.*....",
        ".664.598.."
    )
    check(part1(testInput) == 4361)

    // Read input from a file
    val input = readInput("Day03")
    println("Part 1 result: ${part1(input)}")
    println("Part 2 result: ${part2(input)}")
}
