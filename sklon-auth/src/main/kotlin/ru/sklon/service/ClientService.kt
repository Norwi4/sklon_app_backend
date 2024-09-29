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

    /**
     * Получить пользователя по номеру и коду
     */
    fun loadUserByUsername(phone: String, code: String): UserDetails

    /**
     * Создать нового пользователя и его профиль если такого не существует
     * или обновить код текущего пользователя
     */
    fun createOrUpdateUser(phone: String)

    /**
     * Сохранение нового пользователя
     */
    fun saveUser(client: ClientDto)
}
