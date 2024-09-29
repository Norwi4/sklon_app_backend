package ru.sklon.dao

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.sklon.dao.mapper.ClientRowMapper
import ru.sklon.jooq.domain.sklon_auth.tables.Client.CLIENT
import ru.sklon.jooq.domain.sklon_rent.tables.ClientProfile
import ru.sklon.model.ClientDto
import java.util.*

@Repository
internal class ClientRepositoryImpl(
    private val dsl: DSLContext,
    private val mapper: ClientRowMapper
) : ClientRepository {

    override fun existUser(phone: String): Boolean {
        val ex: Int = dsl.selectOne()
            .from(CLIENT)
            .where(CLIENT.PHONE.eq(phone))
            .fetchCount()
        return ex > 0
    }

    /**
     * Получить пользователя по номеру телефона
     */
    override fun getUser(phone: String): ClientDto {
        return dsl.select(*CLIENT.fields())
            .from(CLIENT)
            .where(CLIENT.PHONE.eq(phone))
            .fetchOne(mapper)!!
    }

    override fun getUserByPhoneAndCode(phone: String, code: String): ClientDto {
        return dsl.select(*CLIENT.fields())
            .from(CLIENT)
            .where(CLIENT.PHONE.eq(phone))
            .and(CLIENT.CODE.eq(code))
            .fetchOne(mapper)!!
    }

    override fun updateUserCode(phone: String, code: String) {
        dsl.update(CLIENT)
            .set(CLIENT.CODE, code)
            .where(CLIENT.PHONE.eq(phone))
            .returningResult(CLIENT.ID)
            .execute()
    }

    override fun createUser(phone: String, code: String): UUID {
        return dsl.insertInto(CLIENT)
            .set(CLIENT.PHONE, phone)
            .set(CLIENT.CODE, code)
            .returningResult(CLIENT.ID)
            .fetchOne()?.value1()!!
    }

    override fun createUserProfile(clientId: UUID) {
        dsl.insertInto(ClientProfile.CLIENT_PROFILE)
            .set(ClientProfile.CLIENT_PROFILE.CLIENT_ID, clientId)
            .returningResult(ClientProfile.CLIENT_PROFILE.ID)
            .execute()
    }

    /**
     * Сохранение пользователя
     */
    override fun saveUser(client: ClientDto) {
        dsl.insertInto(CLIENT)
            .set(CLIENT.PHONE, client.phone)
            .set(CLIENT.CODE, client.code)
            .returningResult(CLIENT.ID)
            .execute()
    }
}
