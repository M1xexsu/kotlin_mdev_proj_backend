package github.mixexsu.application.dtos

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class arriveDTO(
    val arrive_id: Int,
    val bus_id: Int,
    val bus_name: String,
    val station_id: Int,
    val station_name: String,
    val start_station: Int,
    val start_station_name: String,
    val arrive_start: LocalDateTime,
    val end_station: Int,
    val end_station_name: String,
    val arrive_end: LocalDateTime,
    val load: Int,
    val arrive_time: LocalDateTime
)