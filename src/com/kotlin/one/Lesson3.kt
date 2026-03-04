package com.android.com.kotlin.one




data class Weapon(val name: String,val power: Int){

}

interface Healer {
    fun heal(target: Character)
}

interface Attacker {
    fun attack(target: Character): String
}

abstract class Character(val name: String, var maxHp: Int, val weapon: Weapon) {
    private var hp: Int = maxHp
    fun isAlive(): Boolean {
        return hp > 0
    }
    fun receiveDamage(amount: Int) {
        hp -= amount
        if (hp < 0) hp = 0
    }
    fun receiveHealing(amount: Int) {
        if(!isAlive()) return
        hp += amount
    }

    abstract fun typeName(): String
    abstract fun takeTurn(self: Player, opponent: Player, input: ConsoleHelperInput): String

    override fun toString(): String ="${typeName()} $name (HP: $hp/${maxHp}, Weapon: ${weapon.name} with power ${weapon.power})"


}

class Player(val playerName: String) {
    val team: MutableList<Character> = mutableListOf()
    fun aliveCharacters(): List<Character> {
        return team.filter { it.isAlive() }
    }
    fun isDefeated(): Boolean {
        return aliveCharacters().isEmpty()
    }
}



class Warrior(name: String): Character(name, maxHp=220,weapon= Weapon("Sword",25)), Attacker {
    override fun typeName(): String = "Warrior"
    override fun attack(target: Character): String {
        target.receiveDamage(weapon.power)
        return "${typeName()} $name attacks ${target.name} with ${weapon.name} for ${weapon.power} damage!"
    }
    override fun takeTurn(self: Player, opponent: Player, input: ConsoleHelperInput): String {
        val target: Character = input.pickEnemyCharacter(opponent)
        return attack(target)
    }

}

class Magus(name: String):
    Character(name, 160, weapon= Weapon("Staff",20)), Healer, Attacker {
    override fun typeName(): String = "Magus"
    override fun attack(target: Character): String {
        target.receiveDamage(weapon.power)
        return "${typeName()} $name attacks ${target.name} with ${weapon.name} for ${weapon.power} damage!"
    }

    override fun heal(target: Character) {
        val healAmount = 30
        target.receiveHealing(healAmount)
    }

    override fun takeTurn(self: Player, opponent: Player, input: ConsoleHelperInput): String {
        val choice = input.askChoice(
            prompt = "Do you want to attack or heal?",
            choices = listOf("1) Attack an enemy", "2) Heal an ally")
        )
        return when (choice) {
            1 -> {
                val target: Character = input.pickEnemyCharacter(opponent)
                attack(target)
            }

            2 -> {
                val target: Character = input.pickAllyCharacter(self)
                heal(target)
                "${typeName()} $name heals ${target.name} for 30 HP!"
            }

            else -> "Invalid choice. Turn skipped."
        }


    }
}

class Colossus(name: String):
    Character(name, 220, weapon= Weapon("Hammer",25)), Attacker {
    override fun typeName(): String = "Colossus"
    override fun attack(target: Character): String {
        target.receiveDamage(weapon.power)
        return "${typeName()} $name attacks ${target.name} with ${weapon.name} for ${weapon.power} damage!"
    }

    override fun takeTurn(self: Player, opponent: Player, input: ConsoleHelperInput): String {
        val target: Character = input.pickEnemyCharacter(opponent)
        return attack(target)
    }


}

class Dwarf(name: String): Character(name, maxHp = 90, weapon= Weapon("Axe",35)), Attacker {
    override fun typeName(): String = "Dwarf"
    override fun attack(target: Character): String {
        target.receiveDamage(weapon.power)
        return "${typeName()} $name attacks ${target.name} with ${weapon.name} for ${weapon.power} damage!"
    }

    override fun takeTurn(self: Player, opponent: Player, input: ConsoleHelperInput): String {
        val target: Character = input.pickEnemyCharacter(opponent)
        return attack(target)
    }
}

// let's create a helper class to read in the console
class ConsoleHelperInput{
    fun readLineNotNull(): String = readlnOrNull()?.trim().orEmpty()
    fun askChoice(prompt: String, choices: List<String>): Int {
        println(prompt)
        choices.forEach { println(it) }
        print("> ")
        val input = readLineNotNull()
        return input.toIntOrNull() ?: -1
    }
    private fun pickCharacter(player: Player, prompt: String): Character {
        while (true) {
            println(prompt)
            player.aliveCharacters().forEachIndexed { index, character ->
                println("${index + 1}) $character")
            }
            print("> ")
            val choice = readLineNotNull().toIntOrNull()
            if (choice == null || choice < 1 || choice > player.aliveCharacters().size) {
                println("Invalid choice. Please choose a valid character.")
                continue
            }
            return player.aliveCharacters()[choice - 1]
        }
    }

    fun pickEnemyCharacter(opponent: Player): Character{
        return this.pickCharacter(opponent, "Choose an enemy character to attack:")
    }

    fun pickAllyCharacter(current: Player): Character{
        return this.pickCharacter(current, "Choose an ally character to heal:")
    }
}

class Game(private  val input: ConsoleHelperInput = ConsoleHelperInput()) {
    val player1 = Player("Player 1")
    val player2 = Player("Player 2")
    private val usedNames = mutableListOf<String>()
    private var turns = 0
    fun start(){
        println("Welcome to the Battle Game!")

        createTeam(player1)
        createTeam(player2)
        fightLoop()
        endSummary()
    }

    fun createTeam(player: Player) {
        println("${player.playerName}, create your team of 3 characters.")
        val usedTypesInTeam = mutableSetOf<String>()
        while (player.team.size < 3) {
            val choice = input.askChoice(
                prompt="Choose character type for character ${player.team.size + 1}:",
                choices=listOf(
                    "1) Warrior (balance attacker",
                    "2) Magus (can heal allies)",
                    "3) Colossus (very tanky)",
                    "4) Dwarf (high damage but low health"))

                val characterType = when (choice) {
                    1 -> "Warrior"
                    2 -> "Magus"
                    3 -> "Colossus"
                    4 -> "Dwarf"
                    else -> null
                }
            if (characterType == null) {
                println("Invalid choice. Please choose a valid character type.")
                continue
            }
            if (characterType in usedTypesInTeam) {
                println("You have already chosen a $characterType for this team. Please choose a different type.")
                continue
            }
            println("Enter the unique name for this $characterType:")
            val name = input.readLineNotNull()
            if (name.isEmpty()) {
                println("Name cannot be empty. Please enter a valid name.")
                continue
            }
            if (usedNames.contains(name)) {
                println("This name is already taken. Please choose a different name.")
                continue
            }

            val character: Character = when (characterType) {
                "Warrior" -> Warrior(name)
                "Magus" -> Magus(name)
                "Colossus" -> Colossus(name)
                "Dwarf" -> Dwarf(name)
                else -> throw IllegalStateException("Unexpected character type")
            }
            player.team.add(character)
            usedTypesInTeam.add(characterType)
            usedNames.add(name)
        }
    }

    private fun fightLoop() {
        println("\n == Battle Start! ==")
        var current = player1
        var opponent = player2
        while (!player1.isDefeated() && !player2.isDefeated()) {
            turns++
            println("\nTurn $turns: ${current.playerName}'s turn")
            println("${player1.playerName}'s alive characters:")
            player1.aliveCharacters().forEach { println(it) }
            println("\n${player2.playerName}'s alive characters:")
            player2.aliveCharacters().forEach { println(it)}

            val actingCharacter = pickCharacter(current)
            val result = actingCharacter.takeTurn(current, opponent, input)
            println(result)

             // Swap current and opponent for next turn
             val temp = current
             current = opponent
             opponent = temp

        }

    }

    private fun pickCharacter(player: Player): Character{
        while (true){
            println("Choose a character to act:")
            player.aliveCharacters().forEachIndexed { index, character ->
                println("${index + 1}) $character")
            }
            print("> ")
            val choice = input.readLineNotNull().toIntOrNull()
            if (choice == null || choice < 1 || choice > player.aliveCharacters().size) {
                println("Invalid choice. Please choose a valid character.")
                continue
            }
            return player.aliveCharacters()[choice - 1]
        }

    }

    private fun endSummary() {
        val winner = if (player1.isDefeated()) player2 else player1
        println("\nGame Over! ${winner.playerName} wins after $turns turns!")
        println("Final teams:")
        println("${player1.playerName}'s team:")
        player1.team.forEach { println(it) }
        println("\n${player2.playerName}'s team:")
        player2.team.forEach { println(it) }

    }
}


fun main() {

    Game().start()

}
