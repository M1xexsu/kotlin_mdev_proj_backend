package github.mixexsu.application.dtos

import kotlinx.serialization.Serializable

@Serializable
data class busesDTO(
    val bus_id: Int,
    val bus_name: String,
    val station_start: Int,
    val station_end: Int
)