package pt.devhub.antjori.api.encryption.advice

import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

/**
 * Provides a centralized exception handling across all encryption
 * @RequestMapping methods through @ExceptionHandler methods.
 *
 * This class extends the base class {@link ResponseEntityExceptionHandler} and
 * provides an @ExceptionHandler method for handling internal Spring MVC
 * exceptions. This method returns a ResponseEntity for writing to the response
 * with a message converter.
 *
 * @author antjori
*/
private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    /**
     * Handles RestOperationException and its subclasses, building the corresponding
     * response.
     *
     * @param exception
     *            the thrown exception
     * @param request
     *            the request that led to the exception
     * @return Exception response
     */
    @ExceptionHandler(Exception::class)
    fun handleRestOperationException(exception: Exception, request: ServletWebRequest): ResponseEntity<Any> {
        var errorMessage = "An error occurred while trying to "

        errorMessage += if (request.request.requestURI.endsWith("encrypt")) "encrypt" else "decrypt"

        logger.warn(errorMessage, exception)

        return handleExceptionInternal(exception, errorMessage, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
                request)
    }
}
