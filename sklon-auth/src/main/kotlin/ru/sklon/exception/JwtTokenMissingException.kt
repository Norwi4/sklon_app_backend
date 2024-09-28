package ru.sklon.exception

import org.springframework.security.core.AuthenticationException

/**
 *
 * @author Abaev Evgeniy
 */
internal class JwtTokenMissingException(msg: String): AuthenticationException(msg) {
    companion object {
        private const val serialVersionUID = 1L
    }
}
