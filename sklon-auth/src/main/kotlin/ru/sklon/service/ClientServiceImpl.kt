package ru.sklon.service

import org.jooq.DSLContext
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.sklon.dao.ClientRepository
import ru.sklon.jooq.domain.sklon_auth.tables.Client.CLIENT
import ru.sklon.model.ClientDto
import java.util.*
import kotlin.random.Random

@Service
internal class ClientServiceImpl(
    private val repository: ClientRepository,
    private val dsl: DSLContext
) : ClientService, UserDetailsService {
    override fun existUser(phone: String): Boolean {
        return repository.existUser(phone)
    }

    override fun loadUserByUsername(phone: String): UserDetails {
        val client: ClientDto = repository.getUser(phone)
        if (client == null) {
            throw UsernameNotFoundException("Пользователь с номером $phone не найден")
        }
        return User(client.phone, client.code, listOf())
    }

    override fun loadUserByUsername(phone: String, code: String): UserDetails {
        val client: ClientDto = repository.getUserByPhoneAndCode(phone, code)
        if (client == null) {
            throw UsernameNotFoundException("Пользователь с номером $phone не найден")
        }
        return User(client.phone, client.code, listOf())
    }

    override fun createOrUpdateUser(phone: String) {
        val code = Random.nextInt(1000, 10000).toString()
        if (repository.existUser(phone)) {
            repository.updateUserCode(phone, code)
        } else {
            val clientId = repository.createUser(phone, code)
            repository.createUserProfile(clientId)
        }
    }

    override fun saveUser(client: ClientDto) {
        dsl.insertInto(CLIENT)
            .set(CLIENT.PHONE, client.phone)
            .set(CLIENT.CODE, client.code)
            .returningResult(CLIENT.ID)
            .execute()
    }


}
