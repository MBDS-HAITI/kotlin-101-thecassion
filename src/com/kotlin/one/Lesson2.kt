package com.android.com.kotlin.one

fun greet(name: String = "Student"): String {
    // Write a function that greets someone by name. If no name is provided, it should greet "Student" by default.
    return name

}

fun printInfo(name: String, age: Int = 18, city: String = "Paris") {
    // print user info, with some default values. In the format: "$name is $age years old and lives in $city."
    print("$name is $age years old and lives in $city.")
}

fun add(a: Int, b: Int): Int {
    // function that adds two numbers and returns the result
    // Example: add(3, 5) should return 8
    return a + b
}

fun isEven(number: Int): Boolean {
    // check if a number is even. Return true if it is, false otherwise.
    return number % 2 == 0

}

fun areaOfCircle(radius: Double): Double {
    // Compute area of a circle using π * r². Use Math.PI for π.
    return radius*radius*Math.PI
}

// TODO 5: Return a letter grade based on score.
fun grade(score: Int): String {
    /**
     * Return a letter grade based on score. Use if or when
     * - Score >= 90: 'A'
     * - Score >= 80: 'B'
     * - Score >= 70: 'C'
     * - Score >= 60: 'D'
     * - Below 60: 'F'
     */
    when{
        score >= 90 -> return "A"
        score >= 80 -> return "B"
        score >= 70 -> return "C"
        score >= 60 -> return "D"
        else -> return "F"
    }
}

fun maxOfThree(a: Int, b: Int, c: Int): Int {
    /**
     * Return the maximum of three numbers.
     * Example: maxOfThree(3, 9, 6) should return 9
     */
    return maxOf(a, maxOf(b, c))
}

fun toFahrenheit(celsius: Double): Double {
    /**
     * Convert Celsius to Fahrenheit
     * Formula: F = C * 9/5 + 32
     * Example: toFahrenheit(20.0) should return 68.0
     */
    return celsius*9/5 + 32
}


fun applyDiscount(price: Double, discount: Double = 0.1): Double {
    /**
     * Apply a discount to a price. The discount should be a percentage (e.g., 0.1 for 10%).
     * Example: applyDiscount(100.0) should return 90.0
     * Example: applyDiscount(100.0, 0.2) should return 80.0
     */
    return price * (1 - discount)
}


fun capitalizeWords(sentence: String): String {
    /**
     * Capitalize the first letter of each word in a sentence.
     * Example: capitalizeWords(\"hello kotlin world\") should return \"Hello Kotlin World\"
     */
    return sentence.split(" ").joinToString(" ") { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }
}

fun bmi(weight: Double, height: Double): Double {
    /**
     * Compute BMI using the formula: weight / height²
     * Example: bmi(70, 1.75) should return approximately 22.86
     */
    return weight/(height*height)
}

fun passwordStrength(password: String): Boolean {
    /**
     * Check password strength:
     * - At least 8 characters
     * - Contains uppercase letter
     * - Contains lowercase letter
     * - Contains a number
     * Example: passwordStrength("MyPass123") should return true
     * Example: passwordStrength("weak") should return false
     */
    return password.length >= 8 &&
            password.any { it.isUpperCase() } &&
            password.any { it.isLowerCase() } &&
            password.any { it.isDigit() }


}

fun filterEvenNumbers(numbers: List<Int>): List<Int> {
    /**
     * Return a list of even numbers from the input list.
     * Example: filterEvenNumbers(listOf(1, 2, 3, 4, 5, 6)) should return listOf(2, 4, 6)
     */
    return numbers.filter { isEven(it) }
}


fun factorial(n: Int): Int {
    /**
     * Compute the factorial of a number n recursively.
     */
    when{
        n < 0 -> throw IllegalArgumentException("Factorial is not defined for negative numbers")
        n == 0 -> return 1
        else -> return n * factorial(n - 1)
    }
}

fun fibonacci(n: Int): Int {
    /**
     *  Return the nth Fibonacci number using recursion.
     *  Fibonacci sequence: 0, 1, 1, 2, 3, 5, 8, 13, ...
     *  Example: fibonacci(6) should return 8
     */
    when{
        n < 0 -> throw IllegalArgumentException("Fibonacci is not defined for negative numbers")
        n == 0 -> return 0
        n == 1 -> return 1
        else -> return fibonacci(n - 1) + fibonacci(n - 2)
    }
}


fun miniCalculator() {
    /*
    Example
    println("Enter first number:")
    val a = readln().toDouble()
     */
    // Create a simple calculator that takes two numbers and an operator (+, -, *, /) from the user and prints the result.
    while (true){
        println("Enter first number:")
        val a = readln().toDoubleOrNull()
        if (a == null) {
            println("Invalid input. Please enter a valid number.")
            continue
        }

        println("Enter second number:")
        val b = readln().toDoubleOrNull()
        if (b == null) {
            println("Invalid input. Please enter a valid number.")
            continue
        }

        println("Enter operator (+, -, *, /):")
        val operator = readln()

        val result = when (operator) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> if (b != 0.0) a / b else "Error: Division by zero"
            else -> "Invalid operator"
        }

        println("Result: $result")
    }
}


fun analyzeText(text: String): Map<String, Any> {
    /**
     * Analyze the text and return statistics:
     * - Character count
     * - Word count
     * - Longest word
     * - Average word length
     * Example: analyzeText("Kotlin is fun and powerful") should return a map with:
     * {
     *   "charCount": 26,
     *   "wordCount": 5,
     *   "longestWord": "powerful",
     *   "averageWordLength": 5.2
     *   }
     */
    val _listWords = text.split(" ")
    val wordCount = _listWords.size
    // longestWord length
    val longestWordLength = _listWords.maxOf { it.length }

    val longestWord = _listWords.firstOrNull { it.length == longestWordLength } ?: ""
    return mapOf(
        "charCount" to text.length,
        "wordCount" to wordCount,
        "longestWord" to longestWord,
        "averageWordLength" to text.split(" ").map { it.length }.average()
    )
}


fun main() {
    println("🔍 Running Kotlin Functions Playground Tests...\n")

    var passed = 0
    var failed = 0

    fun verify(name: String, block: () -> Boolean) {
        try {
            check(block()) { "❌ Test failed: $name" }
            println("✅ $name")
            passed++
        } catch (e: Throwable) {
            println("❌ $name → ${e.message}")
            failed++
        }
    }

    // 🟢 LEVEL 1
    verify(name = "greet() with default") { greet() == "Student" }
    verify(name = "greet(\"Alice\")") { greet("Alice") == "Alice" }
    verify("printInfo with all defaults") {
        printInfo("Bob")
        true // Just checking it runs without error
    }
    verify("add(3,5) == 8") { add(3, 5) == 8 }
    verify("isEven(4) == true") { isEven(4) }
    verify("isEven(7) == false") { !isEven(7) }
    verify("areaOfCircle(2.0) ≈ 12.57") {
        val result = areaOfCircle(2.0)
        result in 12.56..12.58
    }

    // 🟡 LEVEL 2
    verify("grade(95) == 'A'") { grade(95) == "A" }
    verify("grade(82) == 'B'") { grade(82) == "B" }
    verify("maxOfThree(3,9,6) == 9") { maxOfThree(3, 9, 6) == 9 }
    verify("toFahrenheit(20.0) == 68.0") { (toFahrenheit(20.0) - 68.0).absoluteValue < 0.1 }

    // 🟠 LEVEL 3
    verify("applyDiscount(100.0) == 90.0") { (applyDiscount(100.0) - 90.0).absoluteValue < 0.001 }
    verify("applyDiscount(100.0, 0.2) == 80.0") { (applyDiscount(100.0, 0.2) - 80.0).absoluteValue < 0.001 }

    // 🟣 LEVEL 4
    verify("capitalizeWords works") { capitalizeWords("hello kotlin world") == "Hello Kotlin World" }
    verify("bmi(70,1.75) ≈ 22.86") { bmi(70.0, 1.75) in 22.8..22.9 }
    verify("passwordStrength detects strong") { passwordStrength("MyPass123") }
    verify("passwordStrength detects weak") { !passwordStrength("weak") }
    verify("filterEvenNumbers works") {
        filterEvenNumbers(listOf(1, 2, 3, 4, 5, 6)) == listOf(2, 4, 6)
    }

    // ⚫ LEVEL 5
    verify("factorial(5) == 120") { factorial(5) == 120 }
    verify("fibonacci(6) == 8") { fibonacci(6) == 8 }

    // 🧠 LEVEL 7
    verify("analyzeText stats") {
        val result = analyzeText("Kotlin is fun and powerful")
        result["charCount"] == 26 &&
                result["wordCount"] == 5 &&
                result["longestWord"] == "powerful" &&
                (result["averageWordLength"] as Double) in 4.0..5.0
    }

    println("\n🎯 TEST SUMMARY: $passed passed, $failed failed.")
    if (failed == 0) println("🎉 All tests passed! Great job!")
    else println("⚠️  Some tests failed. Keep debugging!")
}

// Simple helper for double comparison
private val Double.absoluteValue get() = if (this < 0) -this else this


