package pt.devhub.antjori.api.encryption.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.headers.Header
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.Parameter
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
* Open Api configuration class to provide a custom configuration for the API specification
*
* @author antjori
*/
@Configuration
class OpenApiConfiguration {

    @Bean
    fun customOpenApi(@Value("\${springdoc.version}") appVersion: String): OpenAPI =
            // @formatter:off
            OpenAPI().info(Info().title("Encryption API").version(appVersion)
                    .description("Encryption API that expose a set of encryption services. E.g: " +
                            "Galois/Counter Mode (GCM) cryptographic algorithm."))
            .components(
                Components().addSecuritySchemes(
                    "basicScheme",
                    SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")
                ).addParameters(
                    "Authorization",
                    Parameter().`in`("header").schema(StringSchema())
                ).addHeaders(
                    "Authorization",
                    Header().description("Basic Authorization header").schema(StringSchema())))
            // @formatter:on

    @Bean
    fun storeOpenApi(): GroupedOpenApi =
            GroupedOpenApi.builder()
                    .group("gcm-encryption")
                    .pathsToMatch("/gcm-encryption/**")
                    .build()
}