import java.io.File

enum class HandType(val strength: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1)
}

data class Hand(val cards: String, val bid: Int, var rank: Int = 0)

fun main() {
    val hands = readHands(readInput("Day07"))
//    val rankedHandsPart1 = rankHands(hands, false)
//    val totalWinningsPart1 = calculateTotalWinnings(rankedHandsPart1)
//    println("Part 1 - Total winnings: $totalWinningsPart1")

    val rankedHandsPart2 = rankHands(hands, true)
    val totalWinningsPart2 = calculateTotalWinnings(rankedHandsPart2)
    println("Part 2 - Total winnings: $totalWinningsPart2")
}

fun readHands(lines: List<String>): List<Hand> {
    return lines.map { line ->
        val (hand, bid) = line.split(" ")
        Hand(hand, bid.toInt())
    }
}

fun rankHands(hands: List<Hand>, useJokerRule: Boolean): List<Hand> {
    return hands
        .sortedWith(Comparator { h1, h2 -> compareHands(h1, h2, useJokerRule) })
        .mapIndexed { index, hand -> hand.copy(rank = index + 1) }
}

fun compareHands(h1: Hand, h2: Hand, useJokerRule: Boolean): Int {
    val type1 = h1.handType(useJokerRule)
    val type2 = h2.handType(useJokerRule)
    if (type1 != type2) return type2.strength - type1.strength

    val sortedCards1 = h1.sortedCards(useJokerRule)
    val sortedCards2 = h2.sortedCards(useJokerRule)
    sortedCards1.zip(sortedCards2).forEach { (c1, c2) ->
        if (c1 != c2) return cardStrength(c2, useJokerRule) - cardStrength(c1, useJokerRule)
    }

    return 0
}
fun Hand.handType(useJokerRule: Boolean): HandType {
    if (!useJokerRule) {
        return determineHandType(this.cards.groupBy { it }.mapValues { it.value.size })
    }

    // Handling joker rule
    val bestHandType = generatePossibleHands().maxOf { it.handType(useJokerRule = false) }
    println("Optimized hand ${this.cards} with jokers to ${bestHandType}")
    return bestHandType
}

fun Hand.generatePossibleHands(): List<Hand> {
    val possibleHands = mutableListOf<Hand>()
    val jokerCount = this.cards.count { it == 'J' }

    if (jokerCount == 0) {
        possibleHands.add(this)
        return possibleHands
    }

    val nonJokerCards = this.cards.filter { it != 'J' }
    generateCombinations(jokerCount).forEach { combination ->
        possibleHands.add(Hand(nonJokerCards + combination, this.bid))
    }

    return possibleHands
}

fun generateCombinations(jokerCount: Int): List<String> {
    val combinations = mutableListOf<String>()
    val allCards = "AKQJT98765432"

    // Generate all combinations of joker replacements
    allCards.forEach { card ->
        combinations.add(card.toString().repeat(jokerCount))
    }

    return combinations
}

fun determineHandType(counts: Map<Char, Int>): HandType {
    return when {
        counts.any { it.value == 5 } -> HandType.FIVE_OF_A_KIND
        counts.any { it.value == 4 } -> HandType.FOUR_OF_A_KIND
        counts.size == 2 && counts.values.sorted() == listOf(2, 3) -> HandType.FULL_HOUSE
        counts.any { it.value == 3 } -> HandType.THREE_OF_A_KIND
        counts.size == 3 && counts.values.count { it == 2 } == 2 -> HandType.TWO_PAIR
        counts.size == 4 -> HandType.ONE_PAIR
        else -> HandType.HIGH_CARD
    }
}

fun Hand.sortedCards(useJokerRule: Boolean): List<Char> {
    return this.cards.map { if (it == 'J' && useJokerRule) '2' else it }
        .sortedWith(Comparator { c1, c2 -> cardStrength(c2, useJokerRule) - cardStrength(c1, useJokerRule) })
}

fun cardStrength(card: Char, useJokerRule: Boolean): Int {
    val order = "AKQJT98765432"
    return if (useJokerRule && card == 'J') -1 else order.indexOf(card)
}
fun calculateTotalWinnings(hands: List<Hand>): Int {
    return hands.sumOf { it.bid * it.rank }
}
