package com.github.shufflezzz.matcher.automata

import org.antlr.v4.runtime.tree.TerminalNode

class AutomataBuilder {
    fun or(terms: List<NfaEpsAutomata>): NfaEpsAutomata {
        if (terms.size == 1) return terms.single()

        val automata = NfaEpsAutomata.nextAutomata()

        for (term in terms) {
            automata.copyTransitions(term)

            automata.addTransition(automata.initState, term.initState, EpsTerm)
            automata.addTransition(term.termState, automata.termState, EpsTerm)
        }

        return automata
    }

    fun and(terms: List<NfaEpsAutomata>): NfaEpsAutomata {
        if (terms.size == 1) return terms.single()

        return terms.reduce { left, right ->
            left.copyTransitions(right)

            left.addTransition(left.termState, right.initState, EpsTerm)
            left.termState = right.termState

            left
        }
    }

    fun closure(automata: NfaEpsAutomata, operator: TerminalNode?): NfaEpsAutomata {
        if (operator == null) return automata

        return when (operator.symbol.text) {
            "*" -> automata.zeroOrMany()
            "+" -> automata.oneOrMany()
            "?" -> automata.zeroOrOne()
            else -> error("Unknown operator")
        }
    }

    fun char(char: Char?): NfaEpsAutomata {
        if (char == null) error("Unsupported character")

        val automata = NfaEpsAutomata.nextAutomata()

        val term = if (char == '.') AnyTerm else CharTerm(char)
        automata.addTransition(automata.initState, automata.termState, term)

        return automata
    }
}

