package ru.sklon.utils

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.sklon.exception.JwtTokenMalformedException
import ru.sklon.exception.JwtTokenMissingException
import ru.sklon.model.ClientDto
import ru.sklon.vo.ClientVo
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


    fun getUser(token: String): ClientVo? {
        return try {
            val body: Claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body
            val user = ClientVo(body.subject, body["code"].toString())
            return user
        } catch (e: Exception) {
            System.out.println(e.message + " => " + e)
            null
        }

    }

    fun generateToken(client: ClientVo): String {
        val claims: Claims = Jwts.claims().setSubject(client.phone)
        claims.put("code", client.code)
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
