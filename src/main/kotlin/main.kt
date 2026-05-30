package github.mixexsu

import github.mixexsu.data.factory.databaseconnect
import github.mixexsu.application.routing.routing
import github.mixexsu.application.security.jwtConfiguration
import io.ktor.server.engine.*
import io.ktor.server.application.*
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        run()
    }.start(wait = true)
}

fun Application.run() {
    databaseconnect()
    routing()
    jwtConfiguration()
}

