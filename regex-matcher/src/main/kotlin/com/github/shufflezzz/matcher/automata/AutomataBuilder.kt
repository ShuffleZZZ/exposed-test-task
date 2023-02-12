package com.github.shufflezzz.matcher.automata

import org.antlr.v4.runtime.tree.TerminalNode

class AutomataBuilder {
    fun or(terms: List<EpsNFA>): EpsNFA {
        if (terms.size == 1) return terms.single()

        val automation = EpsNFA.create()

        for (term in terms) {
            automation.copyTransitions(term)

            automation.addTransition(automation.initState, term.initState, EpsTerm)
            automation.addTransition(term.termState, automation.termState, EpsTerm)
        }

        return automation
    }

    fun and(terms: List<EpsNFA>): EpsNFA {
        if (terms.size == 1) return terms.single()

        return terms.reduce { left, right ->
            left.copyTransitions(right)

            left.addTransition(left.termState, right.initState, EpsTerm)
            left.termState = right.termState

            left
        }
    }

    fun closure(automation: EpsNFA, operator: TerminalNode?): EpsNFA {
        if (operator == null) return automation

        return when (operator.symbol.text) {
            "*" -> automation.zeroOrMany()
            "+" -> automation.oneOrMany()
            "?" -> automation.zeroOrOne()
            else -> error("Unknown operator")
        }
    }

    fun char(char: Char?): EpsNFA {
        checkNotNull(char) { "Unsupported character" }

        val automation = EpsNFA.create()

        val term = if (char == '.') AnyTerm else CharTerm(char)
        automation.addTransition(automation.initState, automation.termState, term)

        return automation
    }
}

