package com.github.shufflezzz.traverse.services

import com.github.shufflezzz.traverse.dtos.Tree

class TreeDepthService {

    fun maxDepth(root: Tree?): Int {
        if (root == null) return 0

        val leftDepth = maxDepth(root.left)
        val rightDepth = maxDepth(root.right)

        return maxOf(leftDepth, rightDepth) + 1
    }
}
