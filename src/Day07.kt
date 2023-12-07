fun main() {
    fun rankHand(hand: String): Int {
        val ranks = mapOf('2' to 2, '3' to 3, '4' to 4, '5' to 5, '6' to 6, '7' to 7, '8' to 8, '9' to 9, 'T' to 10, 'J' to 11, 'Q' to 12, 'K' to 13, 'A' to 14)
        val cards = hand.map { ranks[it] ?: 0 }
        val counts = cards.groupingBy { it }.eachCount()

        return when {
            counts.containsValue(5) -> 8
            counts.containsValue(4) -> 7
            counts.containsValue(3) && counts.containsValue(2) -> 6
            counts.containsValue(3) -> 5
            counts.count { it.value == 2 } == 2 -> 4
            counts.containsValue(2) -> 3
            else -> 2
        }
    }

    val handComparator = Comparator<Pair<String, Int>> { a, b ->
        val rankComparison = b.second.compareTo(a.second)
        if (rankComparison != 0) return@Comparator rankComparison

        val aCards = a.first.map { it -> "23456789TJQKA".indexOf(it) }
        val bCards = b.first.map { it -> "23456789TJQKA".indexOf(it) }
        for (i in 0 until 5) {
            val cardComparison = bCards[i].compareTo(aCards[i])
            if (cardComparison != 0) return@Comparator cardComparison
        }
        0
    }

    fun part1(input: List<String>): Int {
        val hands = input.map { it.split(" ") }
        val rankedHands = hands.map { it[0] to rankHand(it[0]) }
        val sortedHands = rankedHands.sortedWith(handComparator)

        // Debug output
        sortedHands.forEachIndexed { index, pair ->
            println("Hand: ${pair.first}, Rank: ${pair.second}, Position: ${sortedHands.size - index}")
        }

        return sortedHands.mapIndexed { index, pair -> hands.find { it[0] == pair.first }!![1].toInt() * (sortedHands.size - index) }.sum()
    }

    // Test input
    val testInput = listOf(
        "32T3K 765",
        "T55J5 684",
        "KK677 28",
        "KTJJT 220",
        "QQQJA 483"
    )
    check(part1(testInput) == 6440)

    // Read the full input from the file
    val input = readInput("Day07")
    println("Part 1 result: ${part1(input)}")
}
