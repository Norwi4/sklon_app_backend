package ru.sklon.filter

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.sklon.exception.JwtTokenMissingException
import ru.sklon.model.ClientDto
import ru.sklon.service.ClientService
import ru.sklon.utils.JwtUtil
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *
 * @author Abaev Evgeniy
 */
@Component
internal class JwtAuthenticationFilter(
    private val jwtUtils: JwtUtil,
    private val service: ClientService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var header: String? = request.getHeader("Authorization")

        if (header == null) {
            throw JwtTokenMissingException("No JWT token found in the request headers")
        }

        val token: String = header.substring(7)
        jwtUtils.validateToken(token)

        val user: ClientDto? = jwtUtils.getUser(token)

        val userDetails: UserDetails = service.loadUserByUsername(user!!.phone)

        val usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)

        if (SecurityContextHolder.getContext().authentication == null) {
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        }
        filterChain.doFilter(request, response)

    }



}
