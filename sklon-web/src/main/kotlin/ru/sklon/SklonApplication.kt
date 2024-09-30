package ru.sklon



import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 *
 * @author Abaev Evgeniy
 */
@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
class SklonApplication

fun main(args: Array<String>) {
    runApplication<SklonApplication>(*args)
}



