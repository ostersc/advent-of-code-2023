fun main() {
    fun parseCubes(cubeStr: String): Map<String, Int> {
        return cubeStr.split(", ").associate {
            val (number, color) = it.split(" ")
            color to number.toInt()
        }
    }

    fun isGamePossible(game: String, redLimit: Int, greenLimit: Int, blueLimit: Int): Boolean {
        return game.split("; ").all { subset ->
            val cubes = parseCubes(subset)
            cubes.getOrDefault("red", 0) <= redLimit &&
                    cubes.getOrDefault("green", 0) <= greenLimit &&
                    cubes.getOrDefault("blue", 0) <= blueLimit
        }
    }

    fun part1(input: List<String>): Int {
        val redLimit = 12
        val greenLimit = 13
        val blueLimit = 14

        return input.mapNotNull { game ->
            val (id, sets) = game.removePrefix("Game ").split(": ")
            if (isGamePossible(sets, redLimit, greenLimit, blueLimit)) id.toInt() else null
        }.sum()
    }

    fun part2(input: List<String>): Int {
        // Part 2 implementation goes here
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = listOf(
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
    )
    check(part1(testInput) == 8)

    // Uncomment and use these when real input is available
     val input = readInput("Day02")
     println("Part 1 result: ${part1(input)}")
    // println("Part 2 result: ${part2(input)}")
}

// You may need to implement readInput function if it's not already defined
