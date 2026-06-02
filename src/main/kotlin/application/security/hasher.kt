package github.mixexsu.application.security

import java.security.MessageDigest
import java.util.Base64

fun hashPassword(password: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(password.toByteArray())
    return Base64.getEncoder().encodeToString(hash)
}

fun verifyPassword(password: String, hash: String): Boolean {
    return hashPassword(password) == hash
}