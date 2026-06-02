package github.mixexsu.application.controller

import github.mixexsu.application.operations.createUser
import github.mixexsu.application.operations.readUserById
import github.mixexsu.application.operations.readUserByUsername
import github.mixexsu.application.operations.userExists
import github.mixexsu.application.security.hashPassword
import github.mixexsu.application.security.verifyPassword
import github.mixexsu.application.security.generateToken
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.auth.principal
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(val username: String, val password: String)

@Serializable
data class AuthResponse(val token: String? = null, val message: String, val userId: Int? = null)

fun Application.authController() {
    routing {
        post("/auth/register") {
            try {
                val request = call.receive<AuthRequest>()

                if (request.username.isEmpty() || request.password.isEmpty()) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        AuthResponse(message = "Имя пользователя и пароль не могут быть пустыми")
                    )
                    return@post
                }

                if (userExists(request.username)) {
                    call.respond(
                        HttpStatusCode.Conflict,
                        AuthResponse(message = "Пользователь с таким именем уже существует")
                    )
                    return@post
                }

                val hashedPassword = hashPassword(request.password)

                val userId = createUser(request.username, hashedPassword)

                call.respond(
                    HttpStatusCode.Created,
                    AuthResponse(message = "Регистрация успешна", userId = userId)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    AuthResponse(message = "Ошибка регистрации: ${e.message}")
                )
            }
        }

        post("/auth/login") {
            try {
                val request = call.receive<AuthRequest>()

                val userResult = readUserByUsername(request.username)

                if (userResult == null) {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        AuthResponse(message = "Неверные учетные данные")
                    )
                    return@post
                }

                val (userId, hashedPassword) = userResult

                if (!verifyPassword(request.password, hashedPassword)) {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        AuthResponse(message = "Неверные учетные данные")
                    )
                    return@post
                }

                val user = readUserById(userId)
                    ?: throw Exception("Пользователь не найден")

                val token = generateToken(user)

                call.respond(
                    HttpStatusCode.OK,
                    AuthResponse(token = token, message = "Вход выполнен успешно", userId = userId)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    AuthResponse(message = "Ошибка входа: ${e.message}")
                )
            }
        }

        authenticate("auth-jwt") {
            post("/auth/logout") {
                try {
                    val principal = call.principal<JWTPrincipal>()
                    val username = principal?.payload?.getClaim("username")?.asString()

                    call.respond(
                        HttpStatusCode.OK,
                        AuthResponse(message = "Вы вышли из приложения. До свидания, $username!")
                    )
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        AuthResponse(message = "Ошибка выхода: ${e.message}")
                    )
                }
            }
        }
    }
}