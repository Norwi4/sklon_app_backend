package ru.sklon

import org.jooq.Record
import org.jooq.RecordMapper
import org.springframework.stereotype.Component
import java.util.*


/**
 *
 * @author Abaev Evgeniy
 */
@Component
internal class ClientRecordMapper : RecordMapper<Record, Clients> {
    override fun map(record: Record): Clients? {
        return Clients(
            id = UUID.fromString("UUID.fromString(record[CLIENTS.ID])"),
            firstname = "record[CLIENTS.FIRSTNAME]",
            lastname = "record[CLIENTS.LASTNAME]",
            patronymic = "record[CLIENTS.PATRONYMIC]"
        )
    }
}
