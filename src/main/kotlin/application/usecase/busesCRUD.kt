package github.mixexsu.application.usecase

import github.mixexsu.application.dtos.busesDTO
import github.mixexsu.data.dao.Buses
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun addbus(busesDTO: busesDTO) = transaction {
    Buses.insert {
        it[bus_name] = busesDTO.bus_name
        it[station_start] = busesDTO.station_start
        it[station_end] = busesDTO.station_end
    }
}