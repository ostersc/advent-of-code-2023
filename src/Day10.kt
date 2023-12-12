import java.util.*

fun main() {

    fun isValid(neighbor: Pair<Int, Int>, current: Pair<Int, Int>, graph: Map<Pair<Int, Int>, Char>): Boolean {
        val currentType = graph[current] ?: return false
        val neighborType = graph[neighbor] ?: return false

        return when (currentType) {
            '|', 'S' -> neighborType in setOf('|', 'L', 'J')
            '-' -> neighborType in setOf('-', '7', 'F')
            'L' -> neighborType in setOf('-', '|', 'S') // Connects north and east
            'J' -> neighborType in setOf('-', '|', 'S') // Connects north and west
            '7' -> neighborType in setOf('-', '|', 'S') // Connects south and west
            'F' -> neighborType in setOf('-', '|', 'S') // Connects south and east
            else -> false
        }
    }


    fun dfs(current: Pair<Int, Int>, graph: Map<Pair<Int, Int>, Char>, distances: MutableMap<Pair<Int, Int>, Int>, prev: Pair<Int, Int>?, reverse: Boolean) {
        val neighbors = listOf(
            Pair(current.first + 1, current.second),
            Pair(current.first - 1, current.second),
            Pair(current.first, current.second + 1),
            Pair(current.first, current.second - 1)
        )

        for (neighbor in neighbors) {
            if (isValid(neighbor, current, graph) && neighbor != prev) {
                val newDistance = if (reverse) distances[current]!! - 1 else distances[current]!! + 1
                if (neighbor !in distances || newDistance < distances[neighbor]!!) {
                    distances[neighbor] = newDistance
                    dfs(neighbor, graph, distances, current, reverse)
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val graph = input.mapIndexed { y, row ->
            row.mapIndexed { x, c -> Pair(x, y) to c }
        }.flatten().toMap()

        val start = graph.entries.first { it.value == 'S' }.key
        val distances = mutableMapOf<Pair<Int, Int>, Int>()
        distances[start] = 0
        dfs(start, graph, distances, null, false)

        println("Distances after first traversal:")
        input.indices.forEach { y ->
            input[0].indices.forEach { x ->
                print(distances[Pair(x, y)]?.toString()?.padStart(2, ' ') ?: " .")
            }
            println()
        }

        // Traversing the cycle in the reverse direction
        val maxDistance = distances.values.maxOrNull() ?: 0
        distances[start] = maxDistance
        dfs(start, graph, distances, null, true)

        println("Updated distances after reverse walk:")
        input.indices.forEach { y ->
            input[0].indices.forEach { x ->
                print(distances[Pair(x, y)]?.toString()?.padStart(2, ' ') ?: " .")
            }
            println()
        }

        return distances.values.maxOrNull() ?: 0
    }

    // Test input
    val testInput = listOf(
        ".....",
        ".S-7.",
        ".|.|.",
        ".L-J.",
        "....."
    )
    check(part1(testInput) == 4)

    // Uncomment below lines to test with actual input
    // val input = readInput("Day10")
    // println("Part 1 result: ${part1(input)}")
}
