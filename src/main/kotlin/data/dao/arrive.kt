package github.mixexsu.data.dao

import org.jetbrains.exposed.v1.core.Table

object Arrive : Table("Arrive") {
    val id = integer("arrive_id").autoIncrement()
    val bus_id = integer("bus_id")
    val station_id = integer("station_id")
    val load = integer("load")
    val arrive_time = varchar("arrive_time", 64)

    override val primaryKey = PrimaryKey(station_id)
}