package com.github.shufflezzz.matcher.rules

import com.github.shufflezzz.matcher.assertMatching
import org.junit.jupiter.api.Test

class ConcatMatcherTest {

    @Test
    fun simpleConcatTest() {
        val regex = "abc"

        listOf("", "a", "ab", "abb", "abc", "1abc", "abc1")
            .forEach { assertMatching(it, regex) }
    }

    @Test
    fun concatTest() {
        val regex = "[2468] name(s*)."

        listOf("", "2 names", "24 namesa", "6 name6", "8 namessss", "2 nameas")
            .forEach { assertMatching(it, regex) }
    }

    @Test
    fun parenthesesTest() {
        val regex = "(ab)+c"

        listOf("", "abbc", "ababc", "abababc", "c", "aabc")
            .forEach { assertMatching(it, regex) }
    }
}
