package ru.sklon.dao

import ru.sklon.model.ClientDto

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
     * ПАолучить сущекствуещего клиента
     */
    fun getUser(phone: String): ClientDto

    /**
     * Сохранить клиента
     */
    fun saveUser(client: ClientDto): ClientDto
}
