package github.mixexsu.application.usecase

import github.mixexsu.application.dtos.stationsDTO
import github.mixexsu.data.dao.Stations
import org.jetbrains.exposed.v1.jdbc.insert

fun addstation(stationsDTO: stationsDTO)
{
    Stations.insert {
        it[station_name] = stationsDTO.station_name
    }
}