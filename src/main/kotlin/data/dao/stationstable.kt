package github.mixexsu.data.dao

import org.jetbrains.exposed.v1.core.Table

object Stations : Table("Stations") {
    val station_id = integer("station_id").autoIncrement()
    val station_name = varchar("station_name", 64)

    override val primaryKey = PrimaryKey(station_id)
}