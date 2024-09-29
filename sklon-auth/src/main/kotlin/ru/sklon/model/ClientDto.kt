package ru.sklon.model

import java.util.*

/**
 *
 * @author Abaev Evgeniy
 */
data class ClientDto(
    val id: UUID,
    val phone: String,
    val code: String
)
