package ru.sklon

import org.springframework.web.bind.annotation.*

/**
 * ывапываыпвап
 * @author Abaev Evgeniy
 */

@RestController
@RequestMapping()
internal class ControllerCo(
    private val ser: ServiceCo
) {

    @GetMapping("/objects")
    fun getObjectTrack() = ser.list()

    @PostMapping("/objects")
    fun update(@RequestBody coach: CoachDto) {
        ser.update(coach)
    }

    @GetMapping("/client")
    fun getlistClient() = ser.listClient()

    @PostMapping("/client")
    fun updateClients(@RequestBody clients: ClientsDto) {
        ser.updateClients(clients)
    }

    @GetMapping("get-equipments")
    fun getModels(): List<Model> = listOf(Model(1, "тест1.5", "asf"), Model(2, "asdas", "asdasd"))
}
