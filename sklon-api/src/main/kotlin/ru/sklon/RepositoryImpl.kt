package ru.sklon

import org.jooq.DSLContext
import ru.sklon.jooq.domain.sklon_db.tables.Users.USERS
import org.springframework.stereotype.Repository
import ru.sklon.jooq.domain.sklon_db.tables.Clients.CLIENTS


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
        return dsl.select(*USERS.fields())
            .from(USERS)
            .fetch(mapper)
    }

    override fun update(coach: CoachDto) {
        dsl.insertInto(USERS)
            .set(USERS.NAME, coach.name)
            .set(USERS.L_NAME, coach.l_name)
            .returningResult(USERS.ID)
            .execute()
    }

    override fun listClient(): List<Clients> {
        return dsl.select(*CLIENTS.fields())
            .from(CLIENTS)
            .fetch(mapperClinent)
    }

    override fun updateClients(clients: ClientsDto) {
        dsl.insertInto(CLIENTS)
            .set(CLIENTS.FIRSTNAME, clients.firstname)
            .set(CLIENTS.LASTNAME, clients.lastname)
            .set(CLIENTS.PATRONYMIC, clients.patronymic)
            .returningResult(CLIENTS.ID)
            .execute()
    }
}
