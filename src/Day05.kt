fun main() {
    fun mapValue(map: List<List<Long>>, value: Long): Long {
        for (entry in map) {
            if (entry.size >= 3 && value in entry[1] until entry[1] + entry[2]) {
                val mappedValue = entry[0] + (value - entry[1])
                return mappedValue
            }
        }
        return value // Return the same value if no mapping is found
    }

    fun part1(input: List<String>): Long {
        val sections = input.joinToString("\n").split("\n\n")
        val seeds = sections[0].split(": ")[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() }

        // Create a list of maps for each category, ensuring each map line has at least 3 elements
        val maps = sections.drop(1).map { section ->
            section.split("\n").drop(1).mapNotNull { line ->
                line.split(" ").filter { it.isNotEmpty() }.let {
                    if (it.size >= 3) it.map { num -> num.toLong() } else null
                }
            }
        }

        // Apply mappings sequentially for each seed
        return seeds.map { seed ->
            var current = seed
            maps.forEachIndexed { index, map ->
                current = mapValue(map, current)
            }
            current
        }.minOrNull() ?: Long.MAX_VALUE
    }

    fun part2(input: List<String>): Long {
        // Placeholder for part 2 implementation
        return input.size.toLong()
    }

    // Test input
    val testInput = listOf(
        "seeds: 79 14 55 13",
        "",
        "seed-to-soil map:",
        "50 98 2",
        "52 50 48",
        "",
        "soil-to-fertilizer map:",
        "0 15 37",
        "37 52 2",
        "39 0 15",
        "",
        "fertilizer-to-water map:",
        "49 53 8",
        "0 11 42",
        "42 0 7",
        "57 7 4",
        "",
        "water-to-light map:",
        "88 18 7",
        "18 25 70",
        "",
        "light-to-temperature map:",
        "45 77 23",
        "81 45 19",
        "68 64 13",
        "",
        "temperature-to-humidity map:",
        "0 69 1",
        "1 0 69",
        "",
        "humidity-to-location map:",
        "60 56 37",
        "56 93 4"
    )
    check(part1(testInput) == 35L)

    // Uncomment and use these when real input is available
    // val input = readInput("Day05")
    // println("Part 1 result: ${part1(input)}")
    // println("Part 2 result: ${part2(input)}")
}

// You may want to add helper functions or data classes if necessary.
