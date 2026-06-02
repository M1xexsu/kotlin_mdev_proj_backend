package github.mixexsu.application.dtos

import kotlinx.serialization.Serializable

@Serializable
data class tokensDTO(
    val token_id: Int,
    val user_id: Int,
    val access_token: String
)