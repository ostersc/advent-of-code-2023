fun main() {
    fun countMatches(card: String): Int {
        val (winningNumbers, playerNumbers) = card.substringAfter(": ")
            .split(" | ")
            .map { numbers -> numbers.split(" ").filter(String::isNotEmpty).map(String::toInt) }
        return playerNumbers.count { it in winningNumbers }
    }

    fun part1(input: List<String>): Int {
        return input.map { card ->
            val matches = countMatches(card)
            if (matches > 0) 1.shl(matches - 1) else 0 // Use bitwise shift for doubling
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val cardCounts = MutableList(input.size) { 1 } // Start with one copy of each card

        for (i in input.indices) {
            val matches = countMatches(input[i])
            for (j in 1..matches) {
                if (i + j < input.size) {
                    cardCounts[i + j] += cardCounts[i]
                }
            }
        }

        return cardCounts.sum()
    }

    // Test case
    val testInput = listOf(
        "41 48 83 86 17 | 83 86  6 31 17  9 48 53",
        "13 32 20 16 61 | 61 30 68 82 17 32 24 19",
        " 1 21 53 59 44 | 69 82 63 72 16 21 14  1",
        "41 92 73 84 69 | 59 84 76 51 58  5 54 83",
        "87 83 26 28 32 | 88 30 70 12 93 22 82 36",
        "31 18 13 56 72 | 74 77 10 23 35 67 36 11"
    )
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    // Uncomment and test with real input
     val input = readInput("Day04")
     println("Part 1 result: ${part1(input)}")
     println("Part 2 result: ${part2(input)}")
}