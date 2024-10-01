package ru.sklon.controller

import io.swagger.annotations.SwaggerDefinition
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import ru.sklon.RestControllerAuth
import ru.sklon.TagAuthController
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

/**
 *
 * @author Abaev Evgeniy
 */

@RestControllerAuth
@Tag(name = "Auth service", description = "Сервис авторизации")
internal class AuthController(
    private val service: ClientService,
    private val jwtUtil: JwtUtil
) {

    /**
     * Тестовый эндпоинт skldjfhlasidfjnb
     */
    @GetMapping("/phone")
    fun generateJwtToken(): String {
        /**
         * Пример того, как получать номер авторизованного пользователя
         */
        val phone = SecurityContextHolder.getContext().authentication.name
        return "Все работает. ваш номер - $phone"
    }

    /**
     * Получить одноразовый код по смс
     */
    @GetMapping("/signup/{phone}")
    @Operation(
        summary = "Найти или добавить новый аккаунт по номеру телефона и отправить на " +
                "указанный телефон смс с одноразовым кодом", responses = [
            ApiResponse(responseCode = "200")
        ]
    )
    fun signin(
        @Parameter(description = "Номер телефона (начинается с 7)") @PathVariable("phone") phone: String
    ): ResponseEntity<HttpStatus> {
        service.createOrUpdateUser(phone)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    /**
     * Авторизация по номеру и одноразовому коду
     */
    @PostMapping("/signin")
    @Operation(
        summary = "Авторизация по номеру телефона и одноразовому паролю из смс", responses = [
            ApiResponse(responseCode = "200")
        ]
    )
    fun signup(
        @Parameter(description = "Форма авторизации") @RequestBody authForm: JwtRequest
    ): ResponseEntity<JwtResponse> {
        val userDetails: UserDetails = service.loadUserByUsername(authForm.phone, authForm.code)
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
