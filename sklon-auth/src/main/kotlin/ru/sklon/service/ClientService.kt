package ru.sklon.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import ru.sklon.model.ClientDto

/**
 *
 * @author Abaev Evgeniy
 */
internal interface ClientService:  UserDetailsService {

    /**
     * Проверка на сущекствуещего клиента
     */
    fun existUser(phone: String): Boolean

    fun getUserByUserPhone(): UserDetails

    fun loadUserByUsername(phone: String, code: String): UserDetails

    fun saveUser(client: ClientDto)
}
