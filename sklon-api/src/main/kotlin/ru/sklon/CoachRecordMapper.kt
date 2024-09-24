package ru.sklon

import org.jooq.Record
import org.jooq.RecordMapper
import org.springframework.stereotype.Component
import ru.sklon.jooq.domain.sklon_db.tables.Users.USERS

/**
 * iuyf i
 * @author Abaev Evgeniy
 */
@Component
internal class CoachRecordMapper : RecordMapper<Record, Coach> {
    override fun map(record: Record): Coach {
        return Coach(
            id = record[USERS.ID],
            name = record[USERS.NAME],
            l_name = record[USERS.L_NAME]
        )
    }
}
