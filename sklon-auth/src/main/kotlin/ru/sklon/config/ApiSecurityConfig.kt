package ru.sklon.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.sklon.filter.JwtAuthenticationFilter
import ru.sklon.service.ClientService

/**
 *
 * @author Abaev Evgeniy
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
internal class ApiSecurityConfig(
    private val authService: ClientService,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationEntryPoint: ApiAuthenticationEntryPoint,
): WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/api/**",
            "/swagger/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-ui.html/**",
            "*/**",
            "/open-api-docs/**",
            "/v3/**"
        )
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/signin", "/signup", "/swagger/**", "/swagger-ui/**",
                "/v2/api-docs/**", "/swagger-ui.html/**", "*/**", "/open-api-docs/**", "/v3/**").permitAll()
            .anyRequest().authenticated()
            .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun jwtAuthFilterRegister(filter: JwtAuthenticationFilter): FilterRegistrationBean<JwtAuthenticationFilter> {
        val registrationBean = FilterRegistrationBean(filter)
        registrationBean.isEnabled = false
        return registrationBean
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
