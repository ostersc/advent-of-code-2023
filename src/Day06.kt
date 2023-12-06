fun main() {
    fun calculateMaxDistance(time: Int, record: Int): Int {
        var maxDistance = 0
        for (holdTime in 0 until time) {
            val travelTime = time - holdTime
            val distance = holdTime * travelTime
            if (distance > record) {
                maxDistance += 1
            }
        }
        return maxDistance
    }

    fun part1(input: List<String>): Int {
        val timeList = input[0].split(" ").filter { it.isNotEmpty() }.mapNotNull { it.toIntOrNull() }
        val distanceList = input[1].split(" ").filter { it.isNotEmpty() }.mapNotNull { it.toIntOrNull() }

        val pairs = timeList.zip(distanceList)
        return pairs.map { (time, record) -> calculateMaxDistance(time, record) }.reduce { acc, ways -> acc * ways }
    }

    // Sample input
    val testInput = listOf(
        "Time:      7  15   30",
        "Distance:  9  40  200"
    )
    check(part1(testInput) == 288)

    // Read full input and run the solution
     val input = readInput("Day06")
     println("Part 1 result: ${part1(input)}")
}
