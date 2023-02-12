package com.github.shufflezzz.matcher

import kotlin.test.Test
import kotlin.test.assertEquals

class MatcherTest {
    @Test
    fun negativeNumberTest() {
        val regex = "-[123456789][0123456789]*"

        listOf("", " ", "a", "A", "1", "23", "311", "-1 2", "-12", "-322", "-1 234", "-0", "-0123")
            .forEach { assertMatching(it, regex) }
    }

    @Test
    fun combinedTest() {
        val regex = "(a|b)+ccc[#@!]*( )?"

        listOf("", " ", "a", "aabaccc", "bccc ", "bbbbccc#", "aaccc@@! ")
            .forEach { assertMatching(it, regex) }
    }
}

fun assertMatching(input: String, regex: String) =
    assertEquals(isMatching(input, regex), regex.toRegex().matches(input))
