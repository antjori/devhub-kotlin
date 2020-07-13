package pt.devhub.antjori.api.encryption.configuration.properties

import org.apache.commons.lang3.StringUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * Representation of the user configuration properties.
 *
 * @author antjori
 */
@Configuration
@ConfigurationProperties(prefix = "encryption.security.user")
data class UserConfigProperties(
    /**
     * User role name.
     */
    var name: String = StringUtils.EMPTY,

        /**
     * User role password.
     */
    var password: String = StringUtils.EMPTY
) {
    /**
     * The user role.
     */
    val role = "USER"
}
