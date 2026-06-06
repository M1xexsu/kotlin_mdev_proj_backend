package github.mixexsu.data.dao

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.datetime

object Arrive : Table("Arrive") {
    val id = integer("arrive_id").autoIncrement()
    val bus_id = reference(
        name = "bus_id",
        refColumn = Buses.bus_id,
        onDelete = ReferenceOption.CASCADE
    )
//    val bus_id = reference(
//        name = "bus_id",
//        refColumn = Buses.bus_id,
//        onDelete = ReferenceOption.CASCADE
//    )
    val station_id = reference(
        name = "station_id",
        refColumn = Stations.station_id,
        onDelete = ReferenceOption.CASCADE
    )
    val load = integer("load")
    val arrive_time = datetime("arrive_time")

    // primary key should be the auto-increment id
    override val primaryKey = PrimaryKey(id)
}