package com.github.shufflezzz.matcher.rules

import com.github.shufflezzz.matcher.assertMatching
import org.junit.jupiter.api.Test

class CharMatcherTest {

    @Test
    fun letterTest() {
        val regex = "a"

        listOf("", " ", "a", "A", "b", "aa", "1", "#")
            .forEach { assertMatching(it, regex) }
    }

    @Test
    fun digitTest() {
        val regex = "1"

        listOf("", " ", "1", "2", "a", "12", "#")
            .forEach { assertMatching(it, regex) }
    }

    @Test
    fun symbolTest() {
        val regex = "#"

        listOf("", " ", "a", "A", "b", "aa", "1", "#")
            .forEach { assertMatching(it, regex) }
    }

    @Test
    fun anyTest() {
        val regex = "."

        listOf("", " ", "a", "1", "A", "b", "2", "12", "ab", "ä", "å", "ö", ":")
            .forEach { assertMatching(it, regex) }
    }
}
