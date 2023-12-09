fun main() {
    fun computeNextValue(history: List<Int>): Int {
        val sequences = mutableListOf<List<Int>>()
        var current = history

        while (true) {
            val differences = current.windowed(2, 1).map { it[1] - it[0] }
            sequences.add(differences)
            if (differences.all { it == 0 }) break
            current = differences
        }

        println("Sequences for history $history: $sequences")

        var extrapolated = history.last()
        for (sequence in sequences) {
            extrapolated += sequence.lastOrNull() ?: 0
        }

        println("Next value for history $history: $extrapolated")
        return extrapolated
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { computeNextValue(it.split(" ").map(String::toInt)) }
    }

    // Sample input from the problem description
    val testInput = listOf(
        "0 3 6 9 12 15",
        "1 3 6 10 15 21",
        "10 13 16 21 30 45"
    )

    // Expected result from the problem description
    check(part1(testInput) == 114)

    // Read input (this function is assumed to be implemented)
     val input = readInput("Day09")
     println("Part 1 result: ${part1(input)}")
}

// Uncomment these lines when the readInput function is available.
