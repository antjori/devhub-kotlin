package pt.devhub.antjori.api.encryption.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Bean responsible for returning a custom representation of a full page for a 401 Unauthorized response back to
 * the client.
 *
 * @author antjori
 */
@Component
class EncryptionBasicAuthenticationEntryPoint(
        @Value("\${encryption.security.realm}") var realm: String
) : BasicAuthenticationEntryPoint() {

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        response!!.addHeader("WWW-Authenticate", "Basic realm=\"$realmName\"")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.println("HTTP Status 401 - " + authException!!.message)
    }

    override fun afterPropertiesSet() {
        super.setRealmName(realm)
        super.afterPropertiesSet()
    }
}