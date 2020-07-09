package pt.devhub.antjori.api.encryption.configuration.properties

import org.apache.commons.lang3.StringUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * Representation of the admin configuration properties.
 *
 * @author antjori
 */
@Configuration
@ConfigurationProperties(prefix = "spring.security.user")
data class AdminConfigProperties(
        /**
     * Admin role name.
     */
    var name: String = StringUtils.EMPTY,

        /**
     * Admin role password.
     */
    var password: String = StringUtils.EMPTY
) {
    /**
     * The admin role.
     */
    val role = "ADMIN"
}