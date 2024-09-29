package ru.sklon.dao

import ru.sklon.model.ClientDto
import java.util.*

/**
 *
 * @author Abaev Evgeniy
 */
internal interface ClientRepository {

    /**
     * Проверка на сущекствуещего клиента
     */
    fun existUser(phone: String): Boolean

    /**
     * Получить сущекствуещего клиента
     */
    fun getUser(phone: String): ClientDto

    /**
     * Получить пользователя по номеру и коду
     */
    fun getUserByPhoneAndCode(phone: String, code: String): ClientDto

    /**
     * Обновление одноразового пароля пользователя
     */
    fun updateUserCode(phone: String, code: String)

    /**
     * Сохранить пользвателя и получить его ID
     */
    fun createUser(phone: String, code: String): UUID

    fun createUserProfile(clientId: UUID)

    /**
     * Сохранить клиента
     */
    fun saveUser(client: ClientDto)
}
