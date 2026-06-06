package github.mixexsu.application.controller

import github.mixexsu.application.usecase.getallstations
import github.mixexsu.application.usecase.getarrive
import github.mixexsu.application.usecase.getarriveSortEnd
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

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

        get("/arrive/{arrive}") {
            val raw = call.parameters["arrive"]
            val id = raw?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid arrive id"))
                return@get
            }

            try {
                val result = getarrive(id)
                call.respond(result)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Internal server error"))
                )
            }
        }

        get("/arrive/{arrive}/{end}") {
            val raw = call.parameters["arrive"]
            val rawEnd = call.parameters["end"]
            val id = raw?.toIntOrNull()
            val end = rawEnd?.toIntOrNull()
            if (id == null || end == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid parameters"))
                return@get
            }

            try {
                val result = getarriveSortEnd(id, end)
                call.respond(result)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Internal server error"))
                )
            }
        }
    }
}