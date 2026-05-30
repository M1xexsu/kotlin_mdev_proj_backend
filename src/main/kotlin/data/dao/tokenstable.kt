package github.mixexsu.data.dao

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table

object Tokens : Table("Tokens") {
    val token_id = integer("token_id").autoIncrement()
    val user_id = integer("user_id")
    val token = varchar("token", 512)

    override val primaryKey = PrimaryKey(token_id)
}