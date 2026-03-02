package com.android.com.kotlin.one

fun main() {

    println("👋 Welcome to the Kotlin Playground!")
    println("Let's start learning step by step.\n")


    // ✅ EXERCISE 1 Variables:
    // Create two variables: `city` (String) and `temperature` (Double)
    // Then print: "It is {temperature}°C in {city}"
    // Enforce `city` to be immutable and `temperature` mutable
    // Then print the sentence again after changing `temperature`


    // use val for immutable variable
    val city: String = "Paris"
    // use var for mutable variable
    var temperature: Double = 20.5
    println("It is $temperature°C in $city")
    // change temperature as it is immutable
    temperature = 25.0
    //print the sentence again after changing temperature
    println("It is $temperature°C in $city")


    // ✅ EXERCISE 2 Conditionals:
    // Create a variable `score` (Int)
    // Handle the following cases:
    // - If score is exactly 100, print "Perfect score!"
    // - If score is below 0 or above 100, print "Invalid score"
    // - If score between 0 and 49, print "You failed!"
    // - If score between 50 and 60, print "Just passed!"
    // - If score between 61 and 99, print "Well done!"

    val score: Int = 111

    when {
        // check if score is exactly 100
        score == 100 -> println("Perfect score!")
        // check if score is below 0 or above 100
        score < 0 || score > 100 -> println("Invalid score")
        // check if score is between 0 and 49
        score in 0..49 -> println("You failed!")
        // check if score is between 50 and 60
        score in 50..60 -> println("Just passed!")
        // check if score is between 61 and 99
        score in 61..99 -> println("Well done!")
    }


    // ✅ EXERCISE 3 list and Loops:
    // Create a list of your favorite fruits
    // Loop through the list and print each fruit in uppercase
    // Then, print the total number of fruits in the list
    // Ask the user to enter a fruit name and check if it's in the list

    // create a list of favorite fruits
    val fruits = listOf("Apple", "Banana", "Cherry", "Date", "Elderberry")
    // loop through the list and print each fruit in uppercase
    for (fruit in fruits) {
        println(fruit.uppercase())
    }
    // print the total number of fruits in the list using size property of list
    println("Total number of fruits : ${fruits.size}")
    // ask the user to enter a fruit name and check if it's in the list
    println("Enter a fruit name:")
    val userInput = readLine()
    // check if userInput is not null before checking if it's in the list
    if (userInput != null) {
        if (fruits.any { it.equals(userInput, ignoreCase = true) }) {
            println("$userInput is in the list of favorite fruits!")
        } else {
            println("$userInput is not in the list of favorite fruits.")
        }
    }

    // ✅EXERCISE 4 Elvis Operator:
    // Create a nullable variable `nickname` of type String? and assign it null
    // Print the number of characters in `nickname`
    // Print the nickname or "No nickname provided" if it's null using the Elvis operator
    val nickname: String? = null
    println("Number of characters in nickname: ${nickname?.length ?: 0}")
    // Print the nickname or "No nickname provided" if it's null using the Elvis operator
    println("Nickname: ${nickname ?: "No nickname provided"}")

}

