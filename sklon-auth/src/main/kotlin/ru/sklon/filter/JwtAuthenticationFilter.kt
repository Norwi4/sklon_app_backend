package ru.sklon.filter

import org.jooq.meta.derby.sys.Sys
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
import ru.sklon.vo.ClientVo
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

        val user: ClientVo? = jwtUtils.getUser(token)

        val userDetails: UserDetails = service.loadUserByUsername(user!!.phone, user.code)

        val usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        usernamePasswordAuthenticationToken.setDetails( WebAuthenticationDetailsSource().buildDetails(request))

        val se = SecurityContextHolder.getContext().getAuthentication()
        System.out.println(se)

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            val sec = SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken)
            var f = 0;
        }
        filterChain.doFilter(request, response)
    }
}
