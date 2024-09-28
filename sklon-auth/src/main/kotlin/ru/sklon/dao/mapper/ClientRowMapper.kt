package ru.sklon.dao.mapper

import org.jooq.Record
import org.jooq.RecordMapper
import org.springframework.stereotype.Component
import ru.sklon.jooq.domain.sklon_auth.tables.Clients.CLIENTS
import ru.sklon.model.ClientDto
import java.util.*

/**
 *
 * @author Abaev Evgeniy
 */
@Component
internal class ClientRowMapper : RecordMapper<Record, ClientDto> {
    override fun map(record: Record): ClientDto {
        return ClientDto(
            id = record[CLIENTS.ID],
            phone = record[CLIENTS.PHONE],
            code = record[CLIENTS.CODE],
            token = record[CLIENTS.TOKEN]
        )
    }
}
