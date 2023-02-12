package com.github.shufflezzz.traverse.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TreeDepthRequest(val root: Tree)

@Serializable
data class TreeDepthResponse(@SerialName("max_depth") val maxDepth: Int)

@Serializable
data class Tree(
    val value: String,
    val left: Tree? = null,
    val right: Tree? = null,
)
