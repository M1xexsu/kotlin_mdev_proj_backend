package github.mixexsu.data.dao

import org.jetbrains.exposed.v1.core.Table

object Buses : Table("Buses") {
    val bus_id = integer("bus_id").autoIncrement()
    val bus_name = varchar("bus_name", 64)

    override val primaryKey = PrimaryKey(bus_id)
}