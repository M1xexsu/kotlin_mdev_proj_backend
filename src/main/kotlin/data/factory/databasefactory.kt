package github.mixexsu.data.factory

import github.mixexsu.data.dao.Actions
import github.mixexsu.data.dao.Arrive
import github.mixexsu.data.dao.Buses
import github.mixexsu.data.dao.Stations
import github.mixexsu.data.dao.Tokens
import github.mixexsu.data.dao.Users
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun databaseconnect()
{
    //В идеале это стоит вынести в yaml файл//
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "postgres"
    )
    transaction {
        SchemaUtils.create(Users, Tokens, Stations, Buses, Arrive, Actions)
    }

    println("Database connected")
}
