package github.mixexsu.application.routing

import github.mixexsu.application.controller.actionsController
import github.mixexsu.application.controller.arriveController
import github.mixexsu.application.controller.authController
import io.ktor.server.application.Application

fun Application.routing() {
    authController()
    arriveController()
    actionsController()
}