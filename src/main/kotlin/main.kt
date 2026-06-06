package github.mixexsu

import github.mixexsu.data.factory.databaseconnect
import github.mixexsu.application.routing.routing
import github.mixexsu.application.security.jwtConfiguration
import github.mixexsu.reference.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.application.*
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", ) {
        run()
    }.start(wait = true)
}

fun Application.run() {
    databaseconnect()
    jwtConfiguration()
    // Ensure ContentNegotiation / JSON serialization is installed so call.respond can
    // produce application/json responses (prevents 406 Not Acceptable)
    configureSerialization()
    routing()
}

