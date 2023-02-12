package com.github.shufflezzz.matcher.automata

class NFA(
    private val transitions: Map<State, Set<Transition>>,
    private val initStates: List<State>,
    private val termStates: List<State>,
) {

    fun accepts(value: String) = initStates.any { accepts(it, value) }

    private fun accepts(curState: State, value: String): Boolean {
        if (value.isEmpty()) return curState in termStates

        val forwardTransitions =
            transitions[curState]?.filter { (_, term) -> term.matches(value.first()) } ?: return false

        return forwardTransitions.any { accepts(it.to, value.drop(1)) }
    }
}
