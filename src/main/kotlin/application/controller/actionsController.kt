package github.mixexsu.application.controller

import github.mixexsu.application.dtos.busesDTO
import github.mixexsu.application.dtos.postArriveDTO
import github.mixexsu.application.routing.routing
import github.mixexsu.application.usecase.addbus
import github.mixexsu.application.usecase.pusharrive
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.actionsController() {

    routing{
        authenticate("auth-jwt") {
            post("/admin/bus")
            {
                val request = call.receive<busesDTO>()
                addbus(request)

                call.respond(HttpStatusCode.OK)
            }

            post("/admin/arrive"){
                val request = call.receive<postArriveDTO>()
                pusharrive(request)

                call.respond(HttpStatusCode.OK)
            }

            post("/admin/station"){

                call.respond(HttpStatusCode.OK)
            }
        }
    }
}