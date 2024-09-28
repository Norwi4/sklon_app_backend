package ru.sklon

import org.jooq.DSLContext
import org.springframework.stereotype.Repository


/**
 *
 * @author Abaev Evgeniy
 */
@Repository
internal class RepositoryImpl(
    private val dsl: DSLContext,
    private val mapper: CoachRecordMapper,
    private val mapperClinent: ClientRecordMapper
) : RepositoryCo {
    override fun list(): List<Coach> {
        return listOf()
    }

    override fun update(coach: CoachDto) {
//        dsl.insertInto(USERS)
//            .set(USERS.NAME, coach.name)
//            .set(USERS.L_NAME, coach.l_name)
//            .returningResult(USERS.ID)
//            .execute()
    }

    override fun listClient(): List<Clients> {
        return listOf()
    }

    override fun updateClients(clients: ClientsDto) {
//        dsl.insertInto(CLIENTS)
//            .set(CLIENTS.FIRSTNAME, clients.firstname)
//            .set(CLIENTS.LASTNAME, clients.lastname)
//            .set(CLIENTS.PATRONYMIC, clients.patronymic)
//            .returningResult(CLIENTS.ID)
//            .execute()
    }
}
