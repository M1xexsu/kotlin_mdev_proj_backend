package github.mixexsu.application.usecase

import github.mixexsu.application.dtos.stationsDTO
import github.mixexsu.data.dao.Stations
import github.mixexsu.data.dao.Stations.station_id
import github.mixexsu.data.dao.Stations.station_name
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll

fun addstation(stationsDTO: stationsDTO)
{
    Stations.insert {
        it[station_name] = stationsDTO.station_name
    }
}

fun getallstations(): List<stationsDTO> {
    return Stations.selectAll().withDistinct().map{
        stationsDTO(
            it[station_id],
            it[station_name]
        )
    }
}