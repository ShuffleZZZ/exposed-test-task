package com.github.shufflezzz.matcher.automata

class EpsNFA private constructor(name: String) {
    var initState = name + 0
    var termState = name + 1

    private val transitions = mutableMapOf<State, MutableSet<Transition>>()


    fun copyTransitions(automation: EpsNFA) = transitions.putAll(automation.transitions)

    fun addTransition(from: State, to: State, term: Term) =
        transitions.computeIfAbsent(from) { mutableSetOf() }.add(Transition(to, term))


    fun zeroOrOne() = this.also { addTransition(initState, termState, EpsTerm) }

    fun oneOrMany() = this.also { addTransition(termState, initState, EpsTerm) }

    fun zeroOrMany() = oneOrMany().zeroOrOne()


    fun toNFA(): NFA {
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

        return NFA(nonEpsTransitions, initStates, termStates)
    }


    companion object {
        private var nextName = "@"

        /**
         * Generates [EpsNFA] with unique name.
         */
        fun create() = EpsNFA(nextName())

        private fun nextName(): String {
            nextName = if (nextName.endsWith('Z')) nextName + 'A'
            else nextName.dropLast(1) + nextName.last().inc()

            return nextName
        }
    }
}
