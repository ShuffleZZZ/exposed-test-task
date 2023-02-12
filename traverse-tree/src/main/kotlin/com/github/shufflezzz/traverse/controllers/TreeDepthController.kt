package com.github.shufflezzz.traverse.controllers

import com.github.shufflezzz.traverse.dtos.TreeDepthRequest
import com.github.shufflezzz.traverse.dtos.TreeDepthResponse
import com.github.shufflezzz.traverse.services.TreeDepthService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.treeDepth() {

    val treeDepthService by inject<TreeDepthService>()

    route("tree-depth") {
        post<TreeDepthRequest> {
            call.respond(TreeDepthResponse(treeDepthService.maxDepth(it.root)))
        }
    }
}
