package github.mixexsu.application.usecase

import github.mixexsu.application.dtos.tokensDTO
import github.mixexsu.data.dao.Tokens
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun inserttoken(tokensDTO: tokensDTO) = transaction {
    Tokens.insert {
        it[user_id] = tokensDTO.user_id
        it[token] = tokensDTO.access_token
    }
}

//Нет времени удаления но для меня будто не проблема.
fun revoketoken(accesstoken: String) = transaction {
    Tokens.deleteWhere {
        Tokens.token eq accesstoken
    }
}