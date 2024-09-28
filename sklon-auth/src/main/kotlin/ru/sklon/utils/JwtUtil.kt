package ru.sklon.utils

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.sklon.exception.JwtTokenMalformedException
import ru.sklon.exception.JwtTokenMissingException
import ru.sklon.model.ClientDto
import java.lang.Exception
import java.security.SignatureException
import java.util.*


/**
 *
 * @author Abaev Evgeniy
 */
@Component
internal class JwtUtil {
    @Value("secret")
    private lateinit var jwtSecret: String


    fun getUser(token: String): ClientDto? {
        return try {
            val body: Claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body
            val user = ClientDto(UUID.fromString("b7c4da57-ddcc-43fd-a24c-5e86611cfcc8"), body.subject, "100", "sdfsdfg")
            return user
        } catch (e: Exception) {
            System.out.println(e.message + " => " + e)
            null
        }

    }

    fun generateToken(client: ClientDto): String {
        val claims: Claims = Jwts.claims().setSubject(client.phone)
        val nowMillis: Long = System.currentTimeMillis()
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date(nowMillis))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun validateToken(token: String) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
        } catch (ex: SignatureException) {
            throw JwtTokenMalformedException("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            throw JwtTokenMalformedException("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            throw JwtTokenMalformedException("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            throw JwtTokenMalformedException("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            throw JwtTokenMissingException("JWT claims string is empty.")
        }
    }

}
