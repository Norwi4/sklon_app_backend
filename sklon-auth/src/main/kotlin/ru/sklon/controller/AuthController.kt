package ru.sklon.controller

import org.jooq.DSLContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import ru.sklon.model.PhoneRequest
import ru.sklon.service.ClientService
import ru.sklon.utils.JwtUtil
import ru.sklon.vo.ClientVo
import ru.sklon.vo.JwtRequest
import ru.sklon.vo.JwtResponse
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*
import kotlin.random.Random

/**
 *
 * @author Abaev Evgeniy
 */

@RestController
@RequestMapping()
internal class AuthController(
    private val service: ClientService,
    private val jwtUtil: JwtUtil
) {

    @GetMapping("/phone")
    fun generateJwtToken(): String {
        return "Все работает"
    }

    /**
     * Получить одноразовый код по смс
     */
    @PostMapping("/signup")
    fun signin(@RequestBody phoneRequest: PhoneRequest): ResponseEntity<JwtResponse> {
        service.createOrUpdateUser(phoneRequest.phone)
        return ResponseEntity<JwtResponse>(HttpStatus.OK)
    }

    /**
     * Авторизация по номеру и одноразовому коду
     */
    @PostMapping("/signin")
    fun signup(@RequestBody request: JwtRequest): ResponseEntity<JwtResponse> {
        val userDetails: UserDetails = service.loadUserByUsername(request.phone, request.code)
        return ResponseEntity<JwtResponse>(
            JwtResponse(
                jwtUtil.generateToken(
                    ClientVo(
                        userDetails.username,
                        userDetails.password
                    )
                )
            ),
            HttpStatus.OK
        )
    }



    /**
     * Отправка смс с кодом на указанный номер
     * ТЕСТОВЫЙ МЕТОД
     */
    fun sendCodeInMessage(phone: String, code: String) {
        val request: HttpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(URI("https://email:api_key@gate.smsaero.ru/v2/sms/send?number=$phone&text=$code&sign=SMSAero"))
            .header("accept", "application/json")
            .header("Authorization", getBasicAuthenticationHeader("abaevevgenchik@gmail.com", "qpMUFeQeec2FQJv-_rYzLAvR1lftEBM5"))
            .build()

        val client: HttpClient = HttpClient.newHttpClient();
        val response: HttpResponse<String> = client.send(request, HttpResponse.BodyHandlers.ofString())

        System.out.println(response)
    }

    fun getBasicAuthenticationHeader(phone: String, code: String): String {
        val valueToEncode = "$phone:$code"
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.toByteArray())
    }
}
