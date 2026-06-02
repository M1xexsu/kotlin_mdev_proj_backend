package github.mixexsu.application.dtos

import kotlinx.serialization.Serializable

@Serializable
data class actionsDTO(
    val action_id: Int,
    val user_id: Int? = null,
    val bus_id: Int? = null,
    val arrive_id: Int? = null,
    val station_id: Int = 0,
    val old_value: String
)