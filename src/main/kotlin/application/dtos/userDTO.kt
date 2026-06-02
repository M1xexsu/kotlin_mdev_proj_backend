package github.mixexsu.application.dtos

import kotlinx.serialization.Serializable

@Serializable
data class userDTO(
    val user_id: Int,
    val username: String
    )