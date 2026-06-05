package github.mixexsu.application.usecase

import github.mixexsu.application.dtos.arriveDTO
import github.mixexsu.application.dtos.postArriveDTO
import github.mixexsu.data.dao.Buses
import github.mixexsu.data.dao.Arrive
import github.mixexsu.data.dao.Stations
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.innerJoin
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


/*
Неиронично, но для меня данные функции подобны пытке. Вот что бывает,
когда тебя насильно пересаживают на ktor(не хватает нормального указания связей от слова совсем)
 */
fun getarrive(_id: Int) : List<arriveDTO> = transaction {
    Arrive
        .innerJoin(Buses)
        .innerJoin(Stations)
        .select(Arrive.columns)
        .where {
            (Stations.station_id eq _id) and (Stations.station_id eq Arrive.station_id) and (Buses.bus_id eq Arrive.bus_id)
        }.withDistinct().map{
            arriveDTO(
                it[Arrive.id],
                it[Arrive.bus_id],
                it[Buses.bus_name],
                it[Arrive.station_id],
                it[Stations.station_name],
                it[Buses.station_start],
                Stations.select(Stations.station_name).where { Stations.station_id eq it[Buses.station_start] }.first()[Stations.station_name],
                Arrive.select(Arrive.columns).where { Arrive.bus_id eq Arrive.bus_id}.first()[Arrive.arrive_time],
                it[Buses.station_end],
                Stations.select(Stations.station_name).where { Stations.station_id eq it[Buses.station_end] }.first()[Stations.station_name],
                Arrive.select(Arrive.columns).where { Arrive.bus_id eq Arrive.bus_id}.last()[Arrive.arrive_time],
                it[Arrive.load],
                it[Arrive.arrive_time]
            )
        }
}

//Функция, подобная выше, но также сортирует по конечной точке, а не по всем прибывающим автобусам
fun getarriveSortEnd(_id: Int, _end: Int) : List<arriveDTO> = transaction {
    Arrive
        .innerJoin(Buses)
        .innerJoin(Stations)
        .select(Arrive.columns)
        .where {
            (Stations.station_id eq _id) and (Stations.station_id eq Arrive.station_id) and (Buses.bus_id eq Arrive.bus_id) and (Arrive.station_id eq _end)
        }.withDistinct().map{
            arriveDTO(
                it[Arrive.id],
                it[Arrive.bus_id],
                it[Buses.bus_name],
                it[Arrive.station_id],
                it[Stations.station_name],
                it[Buses.station_start],
                Stations.select(Stations.station_name).where { Stations.station_id eq it[Buses.station_start] }.first()[Stations.station_name],
                Arrive.select(Arrive.columns).where { Arrive.bus_id eq Arrive.bus_id}.first()[Arrive.arrive_time],
                it[Buses.station_end],
                Stations.select(Stations.station_name).where { Stations.station_id eq it[Buses.station_end] }.first()[Stations.station_name],
                Arrive.select(Arrive.columns).where { Arrive.bus_id eq Arrive.bus_id}.last()[Arrive.arrive_time],
                it[Arrive.load],
                it[Arrive.arrive_time]
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