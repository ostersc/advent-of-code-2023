fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = line.firstOrNull { it.isDigit() } ?: '0'
            val lastDigit = line.lastOrNull { it.isDigit() } ?: '0'
            "$firstDigit$lastDigit".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val numberWords = mapOf(
            "one" to 1, "two" to 2, "three" to 3, "four" to 4,
            "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
        )

        val numberWordsBackwards = numberWords.mapKeys { it.key.reversed() }

        fun String.findFirstDigit(): Int {
            var firstDigitValue = 0
            var smallestIndex = this.length

            numberWords.forEach { (word, digit) ->
                val index = this.indexOf(word)
                if (index != -1 && index < smallestIndex) {
                    smallestIndex = index
                    firstDigitValue = digit
                }
            }

            this.forEachIndexed { index, c ->
                if (c.isDigit() && index < smallestIndex) {
                    smallestIndex = index
                    firstDigitValue = c.toString().toInt()
                }
            }

            return if (smallestIndex == this.length) 0 else firstDigitValue
        }

        fun String.findLastDigit(): Int {
            val reversedLine = this.reversed()
            var smallestIndex = this.length
            var lastDigitValue = 0

            numberWordsBackwards.forEach { (word, digit) ->
                val index = reversedLine.indexOf(word)
                if (index != -1 && index < smallestIndex) {
                    smallestIndex = index
                    lastDigitValue = digit
                }
            }

            this.reversed().forEachIndexed { index, c ->
                if (c.isDigit() && index < smallestIndex) {
                    smallestIndex = index
                    lastDigitValue = c.toString().toInt()
                }
            }

            return if (smallestIndex == this.length) 0 else lastDigitValue
        }

        return input.sumOf { line ->
            val firstDigit = line.findFirstDigit()
            val lastDigit = line.findLastDigit()
            firstDigit * 10 + lastDigit
        }
    }

    // Test inputs
    val testInputPart1 = listOf("1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet")
    val testInputPart2 = listOf("two1nine", "eightwothree", "abcone2threexyz", "xtwone3four", "4nineeightseven2", "zoneight234", "7pqrstsixteen")

    // Test if implementation meets criteria from the description
    check(part1(testInputPart1) == 142)
    check(part2(testInputPart2) == 281)

    // Uncomment and use these when real input is available
     val input = readInput("Day01")
     println("Part 1 result: ${part1(input)}")
     println("Part 2 result: ${part2(input)}")
}

// You may need to implement readInput function if it's not already defined
