import kotlin.random.Random

fun main() {
    val playerHand = Hand(mutableListOf<Int>())
    val dealerHand = Hand(mutableListOf<Int>())
    val blackJack = BlackJack(playerHand, dealerHand)

    blackJack.startGame()
}

class BlackJack(var playerHand : Hand, var dealerHand : Hand) {
    fun startGame() {
        var userInput: Int

        playerHand.dealCard()
        playerHand.dealCard()

        if(playerHand.getTotal(true) != 21 && playerHand.getTotal(false) != 21) {
            do {
                println("Your cards: " + playerHand.showCards())
                println("Your total: " + playerHand.getTotal(true) + " (" + playerHand.getTotal(false) + ")")
                println("\nSelection option:\n0 - HIT\n1 - STAY")
                userInput = Integer.valueOf(readLine())

                when(userInput) {
                    0 -> {
                        playerHand.dealCard()
                        playerHand.showCards()
                    }
                    1 -> return finishGame()
                }

            } while(playerHand.getTotal(true) < 21 || playerHand.getTotal(false) < 21)
            return finishGame()
        } else {
            return finishGame()
        }
    }

    private fun finishGame() {
        val playerTotal1 = playerHand.getTotal(true)
        val playerTotal2 = playerHand.getTotal(false)

        println("Your cards: " + playerHand.showCards())
        println("Your total: $playerTotal1 ($playerTotal2)")
        if(playerTotal1 > 21 && playerTotal2 > 21) {
            return println("You lose!")
        } else if(playerTotal1 == 21 || playerTotal2 == 21) {
            return println("BlackJack! You won!")
        }

        dealerHand.dealCard()
        dealerHand.dealCard()

        do {
            dealerHand.dealCard()
        } while(dealerHand.getTotal(true) <= 16 || dealerHand.getTotal(false) <= 16)

        val dealerTotal1 = dealerHand.getTotal(true)
        val dealerTotal2 = dealerHand.getTotal(false)

        println("Dealer cards: " + dealerHand.showCards())
        println("Dealer total: $dealerTotal1 ($dealerTotal2)")

        if(dealerTotal1 > 21 && dealerTotal2 > 21) {
            return println("You won!")
        } else if(dealerTotal1 == 21 || dealerTotal2 == 21) {
            return println("You lose! Dealer won with BlackJack!")
        }

        var playerTotal : Int = 0

        if(playerTotal1 > playerTotal2 && playerTotal1 < 21) {
            playerTotal = playerTotal1
        } else {
            playerTotal = playerTotal2
        }

        var dealerTotal : Int = 0

        if(dealerTotal1 > dealerTotal2 && dealerTotal1 < 21) {
            dealerTotal = dealerTotal1
        } else {
            dealerTotal = dealerTotal2
        }

        if(playerTotal > dealerTotal) {
            return println("You won!")
        } else if(dealerTotal > playerTotal){
            return println("You lose! Dealer won!")
        } else {
            return println("Draw!")
        }
    }
}

class Hand(var cards: MutableList<Int>) {
    fun dealCard() {
        cards.add(Random.nextInt(2, 14))
    }

    fun showCards() : String {
        val stringBuilder = StringBuilder()
        cards.forEach {
            stringBuilder.append(getCardFromValue(it)).append(", ")
        }

        return stringBuilder.removeSuffix(", ").toString()
    }

    private fun getCardFromValue(value : Int) : String {
        when(value) {
            11 -> return "A"
            12 -> return "J"
            13 -> return "Q"
            14 -> return "K"
            else -> return value.toString()
        }
    }

    fun getTotal(ace : Boolean) : Int {
        var total = 0

        cards.forEach {
            if (it < 11) {
                total += it
            } else if (it == 11) {
                if (!ace) {
                    total += 1
                } else {
                    total += it
                }
            } else {
                total += 10
            }
        }

        return total
    }
}

