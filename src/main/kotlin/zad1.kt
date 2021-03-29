import kotlin.random.Random

fun main() {
    val dices = mutableListOf<Dice>()
    for(i in 1..6) dices.add(Dice())

    val game = Yahtzee(dices, 200)
    var userInput: Int

    do {
        println("\nSelect option:\n0 - throw dices (${game.attempts} attempt(s) left)\n1 - check dices\n2 - finish game\n")
        userInput = Integer.valueOf(readLine())

        when(userInput) {
            0 -> {
                for (dice in game.dices) dice.rollDice()
                game.attempts--

                println("You rolled following dices:")
                game.showDices()

                do {
                    println("\nEnter index of dice you would like to lock (use 0 to exit):")
                    userInput = Integer.valueOf(readLine())

                    if (userInput in 1..6) {
                        game.dices[userInput - 1].lockDice()
                    }
                } while (userInput != 0 && !game.areAllLocked())
            }
            1 -> {
                println("Status of current dices:")
                game.showDices()
            }
            2 -> game.attempts = 0
        }

    } while(game.attempts > 0 && userInput != 2 && !game.areAllLocked())

    println("\nEnter index of dice you would like to remove:")
    game.showDices()
    userInput = Integer.valueOf(readLine())
    game.removeDice(userInput - 1)
    game.showDices()

    if(game.checkYahtzee()) {
        println("You have YAHTZEE!")
    } else if(game.checkPoker()) {
        println("You have POKER!")
    } else if(game.checkScale()) {
        println("You have SCALE!")
    } else {
        println("You have no figures.")
    }
}

class Yahtzee (var dices: MutableList<Dice>, var attempts: Int) {
    fun showDices() {
        dices.forEachIndexed { index, dice -> println("${index+1}. number: ${dice.number}, status: ${if(dice.isLocked) "locked" else "unlocked"}") }
    }

    fun checkScale() : Boolean {
        var isScale : Boolean = false;

        for(i in 0 until dices.size -1) {
            if((dices[i].number + 1 != dices[i + 1].number) && (i != 0)) {
                return false;
            }
        }
0
        return true
    }

    fun checkPoker() : Boolean {
        return dices.groupingBy { it.number }.eachCount().any {it.value > 3}
    }

    fun checkYahtzee() : Boolean {
        for(dice in dices) {
            if(dice.number != dices.first().number) return false
        }

        return true
    }

    fun removeDice(index: Int) {
        dices.removeAt(index);
    }

    fun areAllLocked(): Boolean {
        for(dice in dices) {
            if(!dice.isLocked) {
                return false
            }
        }

        return true
    }
}

class Dice {
    var number: Int = 0
    var isLocked: Boolean = false;

    fun rollDice() {
        if(!isLocked) {
            number = Random.nextInt(1, 7)
        }
    }

    fun lockDice() {
        if(isLocked) {
            println("That dice is already locked.")
        } else {
            isLocked = true
        }
    }
}