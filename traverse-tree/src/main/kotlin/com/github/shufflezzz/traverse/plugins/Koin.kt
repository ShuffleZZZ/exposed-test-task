package com.github.shufflezzz.traverse.plugins

import com.github.shufflezzz.traverse.services.TreeDepthService
import io.ktor.server.application.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    val mainModule = module {
        singleOf(::TreeDepthService)
    }

    install(Koin) {
        slf4jLogger()
        modules(mainModule)
    }
}
