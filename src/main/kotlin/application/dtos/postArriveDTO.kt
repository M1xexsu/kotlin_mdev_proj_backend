package github.mixexsu.application.dtos

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class postArriveDTO
    (
        val arrive_id: Int,
        val bus_id: Int,
        val station_id: Int,
        val load: Int,
        val arrive_time: LocalDateTime,
            ){
}