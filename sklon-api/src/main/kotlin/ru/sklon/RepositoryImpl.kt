package ru.sklon

import org.jooq.DSLContext
import ru.sklon.jooq.domain.sklon_db.tables.Users.USERS
import org.springframework.stereotype.Repository


/**
 *
 * @author Abaev Evgeniy
 */
@Repository
internal class RepositoryImpl(
    private val dsl: DSLContext,
    private val mapper: CoachRecordMapper
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
}
