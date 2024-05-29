package com.example

import io.ktor.server.application.*
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}

