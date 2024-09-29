package ru.sklon.controller

import dev.samstevens.totp.code.CodeGenerator
import dev.samstevens.totp.code.DefaultCodeGenerator
import dev.samstevens.totp.code.HashingAlgorithm
import org.jooq.DSLContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import ru.sklon.dao.ClientRepository
import ru.sklon.jooq.domain.sklon_auth.tables.Client.CLIENT
import ru.sklon.model.ClientDto
import ru.sklon.model.Phone
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
    private val dsl: DSLContext,
    private val service: ClientService,
    private val repository: ClientRepository,
    private val jwtUtil: JwtUtil
) {

    @GetMapping("/phone")
    fun generateJwtToken(): String {
        return "Все работает"
    }

    /**
     * Полу
     */
    @PostMapping("/signup")
    fun signin(@RequestBody phone: Phone): ResponseEntity<JwtResponse> {

        val code = Random.nextInt(1000, 10000).toString()

        // проверяем существует ли уже аккаунт с таким номером
        if (service.existUser(phone.phoneNumber)) {
            // обновляем проверочный код в найденом аккаунте

            dsl.update(CLIENT)
                .set(CLIENT.CODE, code)
                .where(CLIENT.PHONE.eq(phone.phoneNumber))
                .returningResult(CLIENT.ID)
                .execute()

            // а так же в таблице истории авторизаций

        } else {
            // нужно создать запись в таблице клиент
            // так же профиль клиента
            // а так же в таблице истории авторизаций
            dsl.insertInto(CLIENT)
                .set(CLIENT.PHONE, phone.phoneNumber)
                .set(CLIENT.CODE, code)
                .returningResult(CLIENT.ID)
                .execute()
        }



        return ResponseEntity<JwtResponse>(HttpStatus.OK)
    }

    @PostMapping("/signin")
    fun signup(@RequestBody request: JwtRequest): ResponseEntity<JwtResponse> {

        val userDetails: UserDetails = service.loadUserByUsername(request.phone, request.code)
        //val user: ClientDto = repository.getUserByPhoneAndCode(userDetails.username, userDetails.password)
        val client = ClientVo(userDetails.username, userDetails.password)

        return ResponseEntity<JwtResponse>(JwtResponse(jwtUtil.generateToken(client)), HttpStatus.OK)
    }



    /**
     * Отправка смс с кодом на указанный номер
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
