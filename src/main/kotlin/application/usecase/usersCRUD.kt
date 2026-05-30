package github.mixexsu.application.operations

import github.mixexsu.data.dao.Users
import github.mixexsu.application.dtos.userDto
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

fun readUserById(id: Int): userDto? = transaction {
    Users.select(Users.user_id, Users.username).where{ Users.user_id eq id }
        .map { row ->
            userDto(
                user_id = row[Users.user_id],
                username = row[Users.username]
            )
        }
        .singleOrNull()
}
