package github.mixexsu.data.dao

import org.jetbrains.exposed.v1.core.Table

object Actions : Table("Actions") {
    val action_id = integer("action_id").autoIncrement()
    val user_id = integer("user_id")
    val bus_id = integer("bus_id").nullable()
    val arrive_id = integer("arrive_id").nullable()
    val station_id = integer("station_id").nullable()
    val old_value = varchar("old_value", 1024) //Стоит заменить на что-то иное

    override val primaryKey = PrimaryKey(action_id)
}