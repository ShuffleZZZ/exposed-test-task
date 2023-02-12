package com.github.shufflezzz.matcher.automata

class NfaEpsAutomata private constructor(name: String) {
    var initState = name + 0
    var termState = name + 1

    private val transitions = mutableMapOf<State, MutableSet<Transition>>()


    fun copyTransitions(automata: NfaEpsAutomata) = transitions.putAll(automata.transitions)

    fun addTransition(from: State, to: State, term: Term) =
        transitions.computeIfAbsent(from) { mutableSetOf() }.add(Transition(to, term))


    fun zeroOrOne() = this.also { addTransition(initState, termState, EpsTerm) }

    fun oneOrMany() = this.also { addTransition(termState, initState, EpsTerm) }

    fun zeroOrMany() = oneOrMany().zeroOrOne()


    fun toNfaAutomata(): NfaAutomata {
        val nonEpsTransitions = transitions
        val initStates = mutableListOf(initState)
        val termStates = mutableListOf(termState)

        var epsPresent = true
        while (epsPresent) {
            epsPresent = false

            for ((from, transitionSet) in nonEpsTransitions) {
                val epsTerms = transitionSet.filter { it.term is EpsTerm }

                for (term in epsTerms) {
                    transitionSet.remove(term)

                    if (from in initStates) initStates.add(term.to)
                    if (term.to in termStates) termStates.add(from)

                    val newTransitions = nonEpsTransitions[term.to]

                    if (newTransitions != null) {
                        transitionSet.addAll(newTransitions)
                        epsPresent = epsPresent || newTransitions.any { it.term is EpsTerm }
                    }
                }
            }
        }

        assert(nonEpsTransitions.values.all { it.all { t -> t.term !is EpsTerm } })

        return NfaAutomata(nonEpsTransitions, initStates, termStates)
    }


    companion object {
        private var nextAutomataName = "@"

        /**
         * Generates [NfaEpsAutomata] with unique name.
         */
        fun nextAutomata() = NfaEpsAutomata(nextName())

        private fun nextName(): String {
            nextAutomataName = if (nextAutomataName.endsWith('Z')) nextAutomataName + 'A'
            else nextAutomataName.dropLast(1) + nextAutomataName.last().inc()

            return nextAutomataName
        }
    }
}
