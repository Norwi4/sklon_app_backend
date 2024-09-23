package ru.sklon

import org.springframework.web.bind.annotation.*

/**
 *
 * @author Abaev Evgeniy
 */

@RestController
@RequestMapping("/api")
internal class ControllerCo(
    private val ser: ServiceCo
) {

    @GetMapping("/objects")
    fun getObjectTrack() = ser.list()

    @PostMapping("/objects")
    fun update(@RequestBody coach: CoachDto) {
        ser.update(coach)
    }
}
