package com.github.shufflezzz.matcher.rules

import com.github.shufflezzz.matcher.assertMatching
import org.junit.jupiter.api.Test

class OrMatcherTest {

    @Test
    fun orTest() {
        val regex = "2|4|6"

        listOf("", "1", "2", "4", "6", "a", "2a", "24")
            .map { assertMatching(it, regex) }
    }

    @Test
    fun singleOrTest() {
        val regex = "a|b"

        listOf("", "a", "A", "b", "aa", "ab", "1", "a|b")
            .map { assertMatching(it, regex) }
    }

    @Test
    fun groupsOrTest() {
        val regex = "(ABC)|(123)|(b3a)"

        listOf("", "abc", "ABC", "123", "b3a", "B3a", "3a", "1BC", "ABC123")
            .map { assertMatching(it, regex) }
    }
}
