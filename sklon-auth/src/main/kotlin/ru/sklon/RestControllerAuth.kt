package ru.sklon

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * REST-контроллер для авторизации пользователей
 *
 * @author Abaev Evgeniy
 */

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@RestController
@RequestMapping("/api/auth")
internal annotation class RestControllerAuth
