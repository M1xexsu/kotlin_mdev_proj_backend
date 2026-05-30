package github.mixexsu.data.dao

import org.jetbrains.exposed.v1.core.Table


object Users : Table("Users")
{
    val user_id = integer(name = "user_id").autoIncrement()
    val username = varchar("username", 256)
    val password = varchar("password", 512)

    override val primaryKey = PrimaryKey(user_id)
}