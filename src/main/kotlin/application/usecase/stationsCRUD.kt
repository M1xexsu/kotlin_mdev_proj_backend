package github.mixexsu.application.usecase

import github.mixexsu.application.dtos.stationsDTO
import github.mixexsu.data.dao.Stations
import github.mixexsu.data.dao.Stations.station_id
import github.mixexsu.data.dao.Stations.station_name
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun addstation(stationsDTO: stationsDTO) = transaction {
    Stations.insert {
        it[station_name] = stationsDTO.station_name
    }
}

fun getallstations(): List<stationsDTO> = transaction {
    Stations.selectAll().withDistinct().map{
        stationsDTO(
            it[station_id],
            it[station_name]
        )
    }
}