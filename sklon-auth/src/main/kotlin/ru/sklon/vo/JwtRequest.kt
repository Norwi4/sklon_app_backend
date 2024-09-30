package ru.sklon.vo

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema

/**
 *
 * @author Abaev Evgeniy
 */

@Schema(description = "Форма для авторизации")
internal data class JwtRequest(
     @field:Parameter(description ="Номер телефона (начинается 7...)")
     val phone: String,

     @field:Parameter(description ="Одноразовый пароль из смс")
     val code: String
)
