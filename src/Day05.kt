fun main() {
    fun mapValue(map: List<List<Long>>, value: Long): Long {
        for (entry in map) {
            if (entry.size >= 3 && value in entry[1] until entry[1] + entry[2]) {
                return entry[0] + (value - entry[1])
            }
        }
        return value // Return the same value if no mapping is found
    }

    fun processSeed(maps: List<List<List<Long>>>, seed: Long): Long {
        var current = seed
        maps.forEach { map ->
            current = mapValue(map, current)
        }
        return current
    }

    fun part1(input: List<String>): Long {
        val sections = input.joinToString("\n").split("\n\n")
        val seedValues = input.first().split(": ")[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        val maps = sections.drop(1).map { section ->
            section.split("\n").drop(1).mapNotNull { line ->
                line.split(" ").filter { it.isNotEmpty() }.let {
                    if (it.size >= 3) it.map { num -> num.toLong() } else null
                }
            }
        }

        return seedValues.map { seed -> processSeed(maps, seed) }.minOrNull() ?: Long.MAX_VALUE
    }

    fun reverseMapValue(map: List<List<Long>>, value: Long): Long {
        for (entry in map.reversed()) {
            val (destinationStart, sourceStart, rangeLength) = entry
            if (entry.size >= 3 && value in destinationStart until destinationStart + rangeLength) {
                return sourceStart + (value - destinationStart)
            }
        }
        return value // Return the same value if no reverse mapping is found
    }

    fun canMapToSeed(maps: List<List<List<Long>>>, seedRanges: List<Pair<Long, Long>>, location: Long): Boolean {
        var current = location
        maps.asReversed().forEach { map ->
            current = reverseMapValue(map, current)
        }
        return seedRanges.any { (start, length) -> current in start until start + length }
    }

    fun findLowestLocation(input: List<String>, seedRanges: List<Pair<Long, Long>>): Long {
        val sections = input.joinToString("\n").split("\n\n")
        val maps = sections.drop(1).map { section ->
            section.split("\n").drop(1).mapNotNull { line ->
                line.split(" ").filter { it.isNotEmpty() }.let {
                    if (it.size >= 3) it.map { num -> num.toLong() } else null
                }
            }
        }

        var location = 0L
        while (!canMapToSeed(maps, seedRanges, location)) {
            location++
        }
        return location
    }

    fun part2(input: List<String>): Long {
        val seedRangeValues = input.first().split(": ")[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        val seedRanges = seedRangeValues.windowed(2, 2, false).map { (start, length) -> start to length }
        return findLowestLocation(input, seedRanges)
    }

    // Test input for both parts
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
    check(part2(testInput) == 46L)

    // Uncomment and use these when real input is available
     val input = readInput("Day05")
     println("Part 1 result: ${part1(input)}")
     println("Part 2 result: ${part2(input)}")
}
