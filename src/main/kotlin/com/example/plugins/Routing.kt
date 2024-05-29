package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.example.routes.databaseRouting

fun Application.configureRouting() {
    routing {
        databaseRouting()
    }
}
