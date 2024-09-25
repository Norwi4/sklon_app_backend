package ru.sklon

/**
 *
 * @author Abaev Evgeniy
 */
internal interface RepositoryCo {
    fun list(): List<Coach>

    fun update(coach: CoachDto)

    fun listClient(): List<Clients>

    fun updateClients(clients: ClientsDto)
}
