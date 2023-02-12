package com.github.shufflezzz.matcher

import com.github.shufflezzz.matcher.automata.AutomataVisitor
import com.github.shufflezzz.matcher.automata.NfaAutomata
import com.github.shufflezzz.matcher.grammar.RegexLexer
import com.github.shufflezzz.matcher.grammar.RegexParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

typealias Regex = String

/**
 * Checks if the [value] matches regex [pattern].
 *
 * The [pattern] is written as a subset of the following regular expression syntax:
 *
 * Matcher:
 * "ABC" - matches the string "ABC".  // exact
 * "[abc123]" - matches any character from the set "abc123".  anyOF
 *
 * "." - matches any character once.
 * "a|b" - matches either "a" or "b".
 *
 * "a+" - matches one or more occurrences of the character "a".
 * "a*" - matches zero or more occurrences of the character "a".
 * "a?" - matches zero or one occurrence of the character "a".
 *
 * To group multiple characters into a single token, use parentheses.
 * For example:
 * "(abc)|(cde)" - matches either "abc" or "cde".
 */
fun isMatching(value: String, pattern: Regex): Boolean {
    val matcher = getMatcher(pattern)

    return matcher.accepts(value)
}

private fun getMatcher(pattern: Regex): NfaAutomata {
    val lexer = RegexLexer(CharStreams.fromString(pattern))
    val tokens = CommonTokenStream(lexer)
    val parser = RegexParser(tokens)
    val visitor = AutomataVisitor()

    return visitor.visit(parser.regex()).toNfaAutomata()
}
