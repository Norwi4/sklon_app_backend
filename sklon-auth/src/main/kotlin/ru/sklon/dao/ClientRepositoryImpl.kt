package ru.sklon.dao

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.sklon.dao.mapper.ClientRowMapper
import ru.sklon.jooq.domain.sklon_auth.tables.Clients.CLIENTS
import ru.sklon.model.ClientDto

@Repository
internal class ClientRepositoryImpl(
    private val dsl: DSLContext,
    private val mapper: ClientRowMapper
) : ClientRepository {

    override fun existUser(phone: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUser(phone: String): ClientDto {
        return dsl.select(*CLIENTS.fields())
            .from(CLIENTS)
            .where(CLIENTS.PHONE.eq(phone))
            .fetchOne(mapper)!!
    }

    override fun saveUser(client: ClientDto): ClientDto {
        TODO("Not yet implemented")
    }
}
