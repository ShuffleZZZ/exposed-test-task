package com.github.shufflezzz.matcher.automata

typealias State = String

sealed interface Term {
    fun matches(c: Char): Boolean
}

data class CharTerm(val token: Char) : Term {
    override fun matches(c: Char) = c == token
}

object AnyTerm : Term {
    override fun matches(c: Char) = true
}

object EpsTerm : Term {
    override fun matches(c: Char) = false
}

data class Transition(val to: State, val term: Term)
