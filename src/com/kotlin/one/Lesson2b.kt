package com.android.com.kotlin.one

val createImmutableList : () -> List<Int> = {listOf(1, 2, 3, 4, 5)}

fun createMutableList(): MutableList<String> {

    val list = mutableListOf<String>("a", "b", "c")
    list.add("d")
    return list
}

fun evenNumbers(numbers: List<Int>): List<Int> {
    return numbers.filter { it % 2 == 0 }
}

fun filterMap(ages: List<Int>): List<String> {
    return ages.filter { it >= 18 }.map { "Adult: $it" }
}

fun flattenList(nestedList: List<List<Any>>): List<Any> {
    return nestedList.flatten()
}

//Exercise 6 — FlatMap
fun flatMapList(list: List<String>): List<String> {
    return list.flatMap {
        it.split(" ")
    }
}

fun eagerProcessing(): List<Int> {
    val start = System.currentTimeMillis()
    val numbers = (1..1000_000).toList()
    // filter numbers divisible by 3
    val filtered = numbers.filter { it % 3 == 0 }
    // Map them by their squares
    val doubled = filtered.map { it * it }
    val firstFive = doubled.take(5)
    val end = System.currentTimeMillis()
    println("eager processiong Time: ${end - start} ms")
    // return only the first 5 results
    return firstFive
}


//Exercise 8 — Lazy Processing

fun lazyProcessing(): List<Int> {
    val start = System.currentTimeMillis()
    val numbers = (1..1000_000).asSequence()
    // filter numbers divisible by 3
    val filtered = numbers.filter { it % 3 == 0 }
    // Map them by their squares
    val doubled = filtered.map { it * it }
    val firstFive = doubled.take(5).toList()
    val end = System.currentTimeMillis()
    println("lazy processing Time: ${end - start} ms")
    // return only the first 5 results
    return firstFive
}

//Exercise 9 — Chain multiple operations
fun filterAndSortNames(names: List<String>): List<String> {
    return names.filter { it.startsWith("A", ignoreCase = true) }
        .map { it.uppercase() }
        .sorted()
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
    verify(name = "createImmutableList") {
        val list1 = createImmutableList()
        val mutableList = list1 as MutableList<Any?>
        try {
            mutableList.add(6)
            false
        }
        catch (_: UnsupportedOperationException) {
            true
        } catch (_: ClassCastException) {
            true
        } catch (_: Throwable) {
            true
        }
    }

    verify(name = "createMutableList") {
        val list2 = createMutableList()
        val mutableList = list2 as MutableList<Any?>
        try {
            mutableList.add(6)
            true
        } catch (_: UnsupportedOperationException) {
            false
        } catch (_: ClassCastException) {
            false
        } catch (_: Throwable) {
            false
        }
    }

    verify(name = "evenNumbers") {
        val numbers = (1..10).toList()
        val evens = evenNumbers(numbers)
        evens == listOf(2, 4, 6, 8, 10)
    }

    verify(name="filterMap") {
        val ages = listOf(11, 12, 15, 22, 30, 17, 40)
        val result = filterMap(ages)
        result == listOf("Adult: 22", "Adult: 30", "Adult: 40")
    }

    verify(name = "flattenList") {
       // val nested = [[1, 2], [3, 4], [5]].toList()
        val nested = listOf(listOf(1, 2), listOf(3, 4), listOf(5))
        val flat = flattenList(nested)

        flat == listOf(1, 2, 3, 4, 5)
    }

    verify(name = "flatMapList") {
        val input = listOf("Kotlin is fun", "I love lists")
        val result = flatMapList(input)
        result == listOf("Kotlin", "is", "fun", "I", "love", "lists")
    }

    verify(name = "eagerProcessing") {
        val result = eagerProcessing()
        result == listOf(9, 36, 81, 144, 225)
    }

    verify(name = "lazyProcessing") {
        val result = lazyProcessing()
        result == listOf(9, 36, 81, 144, 225)
    }

    verify(name = "filterAndSortNames") {
        val names = listOf("Alice", "Bob", "Anna", "Charlie", "Amanda")
        val result = filterAndSortNames(names)
        result == listOf("ALICE", "AMANDA", "ANNA")
    }




    println("\n🎯 TEST SUMMARY: $passed passed, $failed failed.")
    if (failed == 0) println("🎉 All tests passed! Great job!")
    else println("⚠️  Some tests failed. Keep debugging!")
}

// Simple helper for double comparison
private val Double.absoluteValue get() = if (this < 0) -this else this