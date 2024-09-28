package ru.sklon

import org.jooq.Record
import org.jooq.RecordMapper
import org.springframework.stereotype.Component
import java.util.*

/**
 * iuyf iljhvklhjдшнпг
 * @author Abaev Evgeniy
 */
@Component
internal class CoachRecordMapper : RecordMapper<Record, Coach> {
    override fun map(record: Record): Coach {
        return Coach(
            id = "UUID.fromString()",
            name = "record[USERS.NAME]",
            l_name = "record[USERS.L_NAME]"
        )
    }
}
