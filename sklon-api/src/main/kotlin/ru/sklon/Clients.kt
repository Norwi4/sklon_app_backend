package ru.sklon

import java.util.UUID

/**
 *
 * @author Abaev Evgeniy
 */
data class Clients (
    val id: UUID,
    val firstname: String,
    val lastname: String,
    val patronymic: String
)
