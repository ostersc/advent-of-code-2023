fun main() {
    fun navigate(input: List<String>, instructions: String): Int {
        val map = input.associate {
            val (node, connections) = it.split(" = ")
            node to connections.removeSurrounding("(", ")").split(", ").filter { it.isNotEmpty() }
        }

        var current = "AAA"
        var step = 0
        var i = 0

        while (current != "ZZZ") {
            val neighbors = map[current] ?: error("Node $current not found in the map")
            println("Current Node: $current, Neighbors: $neighbors, Instruction: ${instructions[i % instructions.length]}")

            if (neighbors.size == 1) {
                // If there's only one neighbor, always go to that neighbor regardless of the instruction
                current = neighbors[0]
            } else {
                // If there are two neighbors, follow the instruction
                current = if (instructions[i % instructions.length] == 'R') neighbors[1] else neighbors[0]
            }

            i++
            step++
        }

        return step
    }

    fun part1(input: List<String>): Int {
        val instructions = input.first() // Read the instructions from the first line of the input
        return navigate(input.drop(2), instructions) // Pass the node definitions, skipping the first line and the empty line
    }

    // Test with the provided example from the problem description
    val testInput = listOf(
        "LLR",
        "",
        "AAA = (BBB, BBB)",
        "BBB = (AAA, ZZZ)",
        "ZZZ = (ZZZ, ZZZ)"
    )
    check(part1(testInput) == 6)
    println("Part 1 check passed.")

    // Read the full input from the file "Day08"
    val input = readInput("Day08")
    println("Part 1 result: ${part1(input)}")
}
