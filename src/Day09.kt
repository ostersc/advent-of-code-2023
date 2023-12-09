fun main() {
    fun computeDifferences(history: List<Int>): List<List<Int>> {
        val sequences = mutableListOf<List<Int>>()
        var current = history

        while (true) {
            val differences = current.windowed(2, 1).map { it[1] - it[0] }
            sequences.add(differences)
            if (differences.all { it == 0 }) break
            current = differences
        }

        return sequences
    }

    fun extrapolateNextValue(history: List<Int>, sequences: List<List<Int>>): Int {
        var extrapolated = history.last()
        for (sequence in sequences) {
            extrapolated += sequence.lastOrNull() ?: 0
        }
        return extrapolated
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val history = it.split(" ").map(String::toInt)
            val sequences = computeDifferences(history)
            extrapolateNextValue(history, sequences)
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val history = it.split(" ").map(String::toInt).reversed()
            val sequences = computeDifferences(history)
            extrapolateNextValue(history, sequences)
        }
    }

    // Test input from the problem description
    val testInput = listOf(
        "0 3 6 9 12 15",
        "1 3 6 10 15 21",
        "10 13 16 21 30 45"
    )

    // Check for part1 and part2
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    // Read input (this function is assumed to be implemented)
     val input = readInput("Day09")
     println("Part 1 result: ${part1(input)}")
     println("Part 2 result: ${part2(input)}")
}
