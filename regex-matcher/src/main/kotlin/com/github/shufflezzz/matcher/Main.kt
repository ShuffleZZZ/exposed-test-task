package com.github.shufflezzz.matcher

fun main() {
    val numberRegex = "[0123456789]+"
    listOf("0", "1", "12", "35", "123", "1", "5413432")
        .map { assert(isMatching(it, numberRegex)) }
}
