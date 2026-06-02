package github.mixexsu.application.dtos

import kotlinx.serialization.Serializable

@Serializable
data class stationsDTO(
    val station_id: Int,
    val station_name: String
)