package pt.devhub.antjori.api.encryption

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

/**
 * Main entry point to the application.
 *
 * @author antjori
 */
@SpringBootApplication
@ComponentScan(basePackages = ["pt.devhub.antjori.api.encryption"])
class Application

/**
 * Main method for starting the application.
 */
fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
