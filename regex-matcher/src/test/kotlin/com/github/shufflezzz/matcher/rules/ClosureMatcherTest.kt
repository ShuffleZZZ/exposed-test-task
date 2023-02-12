package com.github.shufflezzz.matcher.rules

import com.github.shufflezzz.matcher.assertMatching
import com.github.shufflezzz.matcher.isMatching
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ClosureMatcherTest {

    @Test
    fun oneOrManyTest() {
        val regex = "a+"

        listOf("", " ", "a", "A", "aA", "aa", "aaa", "aaaa", "1aaa", "aaa1")
            .forEach { assertMatching(it, regex) }
    }

    @Test
    fun zeroOrManyTest() {
        val regex = "a*"

        listOf("", " ", "a", "A", "aA", "aa", "aaa", "aaaa", "1aaa", "aaa1")
            .forEach { assertMatching(it, regex) }
    }

    @Test
    fun zeroOrOneTest() {
        val regex = "a?"

        listOf("", " ", "a", "A", "aa", "aaa", "1a", "a1")
            .forEach { assertMatching(it, regex) }
    }

    @Test
    fun closureEqualityTest() {
        val regex1 = "a*"
        val regex2 = "a+?"

        listOf("", " ", "a", "A", "aA", "aa", "aaa", "aaaa", "1aaa", "aaa1")
            .forEach {
                val result1 = isMatching(it, regex1)
                val result2 = isMatching(it, regex2)

                assertEquals(result1, result2)
            }
    }

    @Test
    fun anyCharTest() {
        val regex = ".?"

        listOf("", " ", "a", "A", "1", "@", "aA", "a1b", "bBa", "a23a", "1aaZ")
            .forEach { assertMatching(it, regex) }
    }

    @Test
    fun anySequenceTest() {
        val regex = ".+"

        listOf("", " ", "a", "A", "aA", "a1b", "bBa", "a23a", "1aaZ")
            .forEach { assertMatching(it, regex) }
    }

    @Test
    fun anyEmptySequenceTest() {
        val regex = ".*"

        listOf("", " ", "a", "A", "aA", "a1b", "bBa", "a23a", "1aaZ")
            .forEach { assertMatching(it, regex) }
    }
}
