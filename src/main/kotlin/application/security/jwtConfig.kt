package github.mixexsu.application.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import github.mixexsu.application.dtos.userDTO
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import java.util.*

private const val JWT_SECRET = "secret" // используй System.getenv("JWT_SECRET") в production!
private const val JWT_ISSUER = "ktor-app"
private const val JWT_AUDIENCE = "ktor-app-audience"
private const val JWT_REALM = "Authorized Realm"

fun Application.jwtConfiguration() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = JWT_REALM
            verifier(
                JWT.require(Algorithm.HMAC256(JWT_SECRET))
                    .withIssuer(JWT_ISSUER)
                    .withAudience(JWT_AUDIENCE)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}

fun generateToken(user: userDTO): String {
    return JWT.create()
        .withAudience(JWT_AUDIENCE)
        .withIssuer(JWT_ISSUER)
        .withClaim("username", user.username)
        .withClaim("userId", user.user_id)
        .withExpiresAt(Date(System.currentTimeMillis() + 3600000)) // 1 час
        .sign(Algorithm.HMAC256(JWT_SECRET))
}