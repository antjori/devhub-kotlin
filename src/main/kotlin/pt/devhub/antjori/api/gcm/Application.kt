package pt.devhub.antjori.api.gcm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

/**
 * Main entry point to the application.
 */
@SpringBootApplication
@ComponentScan(basePackages = ["pt.devhub.antjori.api.gcm"])
class Application

/**
 * Main method for starting the application.
 */
fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
