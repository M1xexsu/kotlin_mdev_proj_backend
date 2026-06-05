package github.mixexsu.application.controller

import github.mixexsu.application.dtos.busesDTO
import github.mixexsu.application.routing.routing
import github.mixexsu.application.usecase.getallstations
import github.mixexsu.application.usecase.getarrive
import github.mixexsu.application.usecase.getarriveSortEnd
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.postgresql.gss.MakeGSS.authenticate

fun Application.arriveController()
{
    routing{
        get("/arrive") {
            try {
                call.respond(getallstations())
            }
            catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Internal server error"))
                )
            }
        }

        get("/arrive/{arrive}")
        {
            try {
                val id = call.parameters["arrive"]!!.toInt()
                call.respond(getarrive(id))
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/arrive/{arrive}/{end}")
        {
            try {
                val id = call.parameters["arrive"]!!.toInt()
                val end = call.parameters["end"]!!.toInt()
                call.respond(getarriveSortEnd(id, end))
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}