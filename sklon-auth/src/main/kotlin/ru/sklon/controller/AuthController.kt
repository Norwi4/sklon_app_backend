package ru.sklon.controller

import org.jooq.DSLContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import ru.sklon.dao.ClientRepository
import ru.sklon.jooq.domain.sklon_auth.tables.Clients.CLIENTS
import ru.sklon.model.ClientDto
import ru.sklon.model.Phone
import ru.sklon.service.ClientService
import ru.sklon.utils.JwtUtil
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

    @PostMapping("/signup")
    fun signup(@RequestBody request: JwtRequest): ResponseEntity<JwtResponse> {
        // получаем номер, делаем запрос на получение смс, находим в базе sklon_auth.clients профиль
        // и перезаписываем код либо создаем новый, так же создаем профиль клиента в sklon_rent.clients_profiles
        // и создаем, если его нет

        val ex: Int = dsl.selectOne()
            .from(CLIENTS)
            .where(CLIENTS.PHONE.eq(request.phone))
            .fetchCount()

        System.out.println(ex)
        System.out.println("                     ")
        System.out.println(request)
        System.out.println("                     ")

//        val request: HttpRequest = HttpRequest.newBuilder()
//            .GET()
//            .uri(URI("https://email:api_key@gate.smsaero.ru/v2/sms/send?number=79056402045&text=1008&sign=SMSAero"))
//            .header("accept", "application/json")
//            .header("Authorization", getBasicAuthenticationHeader("abaevevgenchik@gmail.com", "qpMUFeQeec2FQJv-_rYzLAvR1lftEBM5"))
//            .build()
//
//        val client: HttpClient = HttpClient.newHttpClient();
//        val response: HttpResponse<String> = client.send(request, HttpResponse.BodyHandlers.ofString())
//
//        System.out.println(response)


        System.out.println("")
        System.out.println("")
        System.out.println("")
        System.out.println("токен")

        val userDetails: UserDetails = service.loadUserByUsername(request.phone)

        val phone: String = userDetails.username

        val user: ClientDto = repository.getUser(phone)
        val token: String = jwtUtil.generateToken(user)


        return ResponseEntity<JwtResponse>(JwtResponse(token), HttpStatus.OK)
    }

    fun getCodeByPhone(phone: String) {

    }

    fun getBasicAuthenticationHeader(phone: String, code: String): String {
        val valueToEncode = "$phone:$code"
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.toByteArray())
    }
}
