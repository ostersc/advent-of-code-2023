fun main() {
    fun calculateMaxDistance(time: Long, record: Long): Long {
        var maxDistance = 0L
        for (holdTime in 0L until time) {
            val travelTime = time - holdTime
            val distance = holdTime * travelTime
            if (distance > record) {
                maxDistance += 1
            }
        }
        return maxDistance
    }

    fun part1(input: List<String>): Long {
        val timeList = input[0].split(" ").filter { it.isNotEmpty() }.mapNotNull { it.toLongOrNull() }
        val distanceList = input[1].split(" ").filter { it.isNotEmpty() }.mapNotNull { it.toLongOrNull() }

        val pairs = timeList.zip(distanceList)
        return pairs.map { (time, record) -> calculateMaxDistance(time, record) }.reduce { acc, ways -> acc * ways }
    }

    fun part2(input: List<String>): Long {
        val time = input[0].filter { it.isDigit() }.toLong()
        val record = input[1].filter { it.isDigit() }.toLong()

        return calculateMaxDistance(time, record)
    }

    // Sample input for both parts
    val testInput = listOf(
        "Time:      7  15   30",
        "Distance:  9  40  200"
    )
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    // Read full input and run the solution
     val input = readInput("Day06")
     println("Part 1 result: ${part1(input)}")
     println("Part 2 result: ${part2(input)}")
}

// Additional utility function `readInput` might be needed to read the actual problem input
