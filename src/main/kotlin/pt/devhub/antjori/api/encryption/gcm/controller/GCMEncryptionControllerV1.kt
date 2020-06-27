package pt.devhub.antjori.api.encryption.gcm.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pt.devhub.antjori.api.encryption.gcm.service.GCMEncryptionService

/**
 * GCM encryption controller responsible for exposing the available REST API.
 *
 * @author antjori
 */
@RestController
@RequestMapping(value = ["/gcm-encryption/v1"])
@Tag(name = "GCM Encryption API", description = "A RESTful service that allows the encryption/decryption according to" +
        " the Galois/Counter Mode cryptographic algorithm.")
class GCMEncryptionControllerV1(val encryptionService: GCMEncryptionService) {

    @Operation(summary = "GCM encryption service", description = "Allows the encryption of a given text", method = "GET", tags = ["GCM Encryption", "encryption"])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "The request has been successful, the response will contain an entity corresponding to the requested resource"),
        ApiResponse(responseCode = "500", description = "The request has not been successful due to a unexpected technical exception")
    ])
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = ["/encrypt"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun encrypt(text: String): ResponseEntity<String> = ResponseEntity.ok(encryptionService.encrypt(text))

    @Operation(summary = "GCM decryption service", description = "Allows the decryption of a given secret", method = "GET", tags = ["GCM Encryption", "decryption"])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "The request has been successful, the response will contain an entity corresponding to the requested resource"),
        ApiResponse(responseCode = "500", description = "The request has not been successful due to a unexpected technical exception")
    ])
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = ["/decrypt"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun decrypt(secret: String): ResponseEntity<String> = ResponseEntity.ok(encryptionService.decrypt(secret))
}
