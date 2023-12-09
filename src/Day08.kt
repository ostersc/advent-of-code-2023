fun main() {
    fun gcd(x: Long, y: Long): Long {
        return if (y == 0L) x else gcd(y, x % y)
    }

    fun lcm(a: Long, b: Long): Long {
        return a / gcd(a, b) * b
    }

    fun navigate(input: List<String>, instructions: String, startNode: String): Long {
        val map = input.drop(2).associate {
            val (node, connections) = it.split(" = ")
            node to connections.removeSurrounding("(", ")").split(", ")
        }

        var current = startNode
        var step = 0L
        var i = 0

        while (current != "ZZZ") {
            val neighbors = map[current] ?: error("Node $current not found in the map")
            current = if (instructions[i % instructions.length] == 'R') neighbors[1] else neighbors[0]
            i++
            step++

            // Break if a node loops back on itself
            if (current.endsWith("Z") && (neighbors[0] == current || neighbors[1] == current)) {
                break
            }
        }

        return step
    }

    fun part1(input: List<String>): Long {
        val instructions = input.first()
        return navigate(input, instructions, "AAA")
    }

    fun part2(input: List<String>): Long {
        val instructions = input.first()
        val startNodes = input.drop(2).map { it.split(" = ")[0] }.filter { it.endsWith("A") }
        val stepCounts = startNodes.map { startNode -> navigate(input, instructions, startNode) }

        // Calculate the LCM of all step counts
        return stepCounts.reduce { acc, steps -> lcm(acc, steps) }
    }

    // Test if implementation meets criteria from the description, like:
    val testInput = listOf(
        "LLR",
        "",
        "AAA = (BBB, BBB)",
        "BBB = (AAA, ZZZ)",
        "ZZZ = (ZZZ, ZZZ)"
    )
    check(part1(testInput) == 6L)
    println("Part 1 check passed.")

    val testInputPart2 = listOf(
        "LR",
        "",
        "11A = (11B, XXX)",
        "11B = (XXX, 11Z)",
        "11Z = (11B, XXX)",
        "22A = (22B, XXX)",
        "22B = (22C, 22C)",
        "22C = (22Z, 22Z)",
        "22Z = (22B, 22B)",
        "XXX = (XXX, XXX)"
    )
    check(part2(testInputPart2) == 6L)
    println("Part 2 check passed.")

    // Uncomment and replace "Day??" with the actual day when ready to use
     val input = readInput("Day08")
     println("Part 1 result: ${part1(input)}")
     println("Part 2 result: ${part2(input)}")
}
