package com.github.shufflezzz.matcher.rules

import com.github.shufflezzz.matcher.assertMatching
import com.github.shufflezzz.matcher.isMatching
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RangeMatcherTest {

    @Test
    fun rangeTest() {
        val regex = "[123]"

        listOf("", "1", "2", "3", "4", "a", "1a", "12")
            .map { assertMatching(it, regex) }
    }

    @Test
    fun singleRangeTest() {
        val regex = "[a]"

        listOf("", "a", "A", "b", "aa", "1")
            .map { assertMatching(it, regex) }
    }

    @Test
    fun orEqualityTest() {
        val orRegex = "1|2|3"
        val rangeRegex = "[123]"

        listOf("", "1", "2", "3", "4", "a", "1a", "12")
            .map {
                val orResult = isMatching(it, orRegex)
                val rangeResult = isMatching(it, rangeRegex)

                assertEquals(orResult, rangeResult)
            }
    }
}
