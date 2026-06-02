package github.mixexsu.application.operations

import github.mixexsu.data.dao.Users
import github.mixexsu.application.dtos.userDTO
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.collections.singleOrNull


fun createUser(username: String, password: String): Int = transaction {
    Users.insert {
        it[Users.username] = username
        it[Users.password] = password
    } get Users.user_id
}

fun readUserById(id: Int): userDTO? = transaction {
    Users.select(Users.user_id, Users.username).where{ Users.user_id eq id }
        .map { row ->
            userDTO(
                user_id = row[Users.user_id],
                username = row[Users.username]
            )
        }
        .singleOrNull()
}

fun readUserByUsername(username: String): Pair<Int, String>? = transaction {
    Users.select(Users.user_id, Users.username, Users.password).where {
        Users.username eq username
    }
        .map { row ->
            Pair(row[Users.user_id], row[Users.password]) // возвращаем id и хеш пароля
        }
        .singleOrNull()
}

fun userExists(username: String): Boolean = transaction {
    Users.select(Users.user_id).where {
        Users.username eq username
    }.count() > 0
}