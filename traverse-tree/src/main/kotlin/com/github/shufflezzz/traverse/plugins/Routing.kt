package com.github.shufflezzz.traverse.plugins

import com.github.shufflezzz.traverse.controllers.treeDepth
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("api/v1/") {
            treeDepth()
        }
    }
}
