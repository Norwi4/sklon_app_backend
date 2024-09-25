package ru.sklon

import org.springframework.stereotype.Service

/**
 *
 * @author Abaev Evgeniy
 */
@Service
internal class ServiceImpl(
    private val rep: RepositoryCo
): ServiceCo {
    override fun list(): List<Coach> {
        return rep.list()
    }

    override fun update(coach: CoachDto) {
        rep.update(coach)
    }

    override fun listClient(): List<Clients> {
        return rep.listClient()
    }

    override fun updateClients(clients: ClientsDto) {
        rep.updateClients(clients)
    }
}
