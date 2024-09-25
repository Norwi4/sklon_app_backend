package ru.sklon

/**
 *
 * @author Abaev Evgeniy
 */
internal interface ServiceCo {
    fun list(): List<Coach>

    fun update(coach: CoachDto)

    fun listClient(): List<Clients>

    fun updateClients(clients: ClientsDto)
}
