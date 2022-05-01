package com.sometime

import com.sometime.di.mainModule
import io.ktor.application.*
import com.sometime.plugins.*
import org.koin.ktor.ext.Koin
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(Koin) {
        modules(mainModule)
    }
    configureSecurity()
    configureMonitoring()
    configureSockets()
    configureRouting()
    configureSerialization()
}
