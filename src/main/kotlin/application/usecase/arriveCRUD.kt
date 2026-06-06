package github.mixexsu.application.usecase

import github.mixexsu.application.dtos.arriveDTO
import github.mixexsu.application.dtos.postArriveDTO
import github.mixexsu.data.dao.Buses
import github.mixexsu.data.dao.Arrive
import github.mixexsu.data.dao.Stations
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


/*
Неиронично, но для меня данные функции подобны пытке. Вот что бывает,
когда тебя насильно пересаживают на ktor(не хватает нормального указания связей от слова совсем)
 */
fun getarrive(_id: Int) : List<arriveDTO> = transaction {
    (Arrive
        .innerJoin(Buses)
        .innerJoin(Stations))
        .selectAll()
        .where { (Stations.station_id eq _id) and (Arrive.station_id eq Stations.station_id) and (Arrive.bus_id eq Buses.bus_id) }
        .withDistinct()
        .map { row ->
            val busId = row[Arrive.bus_id]
            val startStationId = row[Buses.station_start]
            val endStationId = row[Buses.station_end]

            // find arrival time at start station for this bus (if exists)
            val arriveStart = Arrive
                .select(Arrive.columns)
                .where { (Arrive.bus_id eq busId) and (Arrive.station_id eq startStationId) }
                .map { r -> r[Arrive.arrive_time] }
                .firstOrNull()

            // find arrival time at end station for this bus (if exists)
            val arriveEnd = Arrive
                .select(Arrive.columns)
                .where { (Arrive.bus_id eq busId) and (Arrive.station_id eq endStationId) }
                .map { r -> r[Arrive.arrive_time] }
                .lastOrNull()

            val startStationName = Stations
                .select(Stations.columns)
                .where { Stations.station_id eq startStationId }
                .map { r -> r[Stations.station_name] }
                .firstOrNull() ?: ""

            val endStationName = Stations
                .select(Stations.columns)
                .where { Stations.station_id eq endStationId }
                .map { r -> r[Stations.station_name] }
                .firstOrNull() ?: ""

            arriveDTO(
                row[Arrive.id],
                busId,
                row[Buses.bus_name],
                row[Arrive.station_id],
                row[Stations.station_name],
                startStationId,
                startStationName,
                arriveStart ?: row[Arrive.arrive_time],
                endStationId,
                endStationName,
                arriveEnd ?: row[Arrive.arrive_time],
                row[Arrive.load],
                row[Arrive.arrive_time]
            )
        }
}

// Функция, аналогичная getarrive, но фильтрует по конечной станции автобуса (Buses.station_end)
fun getarriveSortEnd(_id: Int, _end: Int) : List<arriveDTO> = transaction {
    (Arrive
        .innerJoin(Buses)
        .innerJoin(Stations))
        .selectAll()
        .where { (Stations.station_id eq _id) and (Arrive.station_id eq Stations.station_id) and (Arrive.bus_id eq Buses.bus_id) and (Buses.station_end eq _end) }
        .withDistinct()
        .map { row ->
            val busId = row[Arrive.bus_id]
            val startStationId = row[Buses.station_start]
            val endStationId = row[Buses.station_end]

            val arriveStart = Arrive
                .select(Arrive.columns)
                .where { (Arrive.bus_id eq busId) and (Arrive.station_id eq startStationId) }
                .map { r -> r[Arrive.arrive_time] }
                .firstOrNull()

            val arriveEnd = Arrive
                .select(Arrive.columns)
                .where { (Arrive.bus_id eq busId) and (Arrive.station_id eq endStationId) }
                .map { r -> r[Arrive.arrive_time] }
                .lastOrNull()

            val startStationName = Stations
                .select(Stations.columns)
                .where { Stations.station_id eq startStationId }
                .map { r -> r[Stations.station_name] }
                .firstOrNull() ?: ""

            val endStationName = Stations
                .select(Stations.columns)
                .where { Stations.station_id eq endStationId }
                .map { r -> r[Stations.station_name] }
                .firstOrNull() ?: ""

            arriveDTO(
                row[Arrive.id],
                busId,
                row[Buses.bus_name],
                row[Arrive.station_id],
                row[Stations.station_name],
                startStationId,
                startStationName,
                arriveStart ?: row[Arrive.arrive_time],
                endStationId,
                endStationName,
                arriveEnd ?: row[Arrive.arrive_time],
                row[Arrive.load],
                row[Arrive.arrive_time]
            )
        }
}

//функция для отправки тела запроса на добавление новой записи о прибытии
//Перегружен
fun pusharrive(_l: postArriveDTO) = transaction {
    Arrive.insert {
        it[bus_id] = _l.bus_id
        it[station_id] = _l.station_id
        it[load] = _l.load
        it[arrive_time] = _l.arrive_time
    }
}

fun pusharrive(_l: List<postArriveDTO>) = transaction {
    _l.forEach {_a ->
        Arrive.insert {
            it[bus_id] = _a.bus_id
            it[station_id] = _a.station_id
            it[load] = _a.load
            it[arrive_time] = _a.arrive_time
        }
    }
}