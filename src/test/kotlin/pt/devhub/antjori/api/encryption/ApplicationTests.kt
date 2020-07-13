package pt.devhub.antjori.api.encryption

import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.RequestPostProcessor
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import pt.devhub.antjori.api.encryption.configuration.properties.UserConfigProperties
import java.util.*

/**
 * Integration test class for this Spring Boot application.
 *
 * @author antjori
 */
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class ApplicationTests(
        @Autowired val mockMvc: MockMvc,
        @Autowired val userConfigProperties: UserConfigProperties
) {

    /**
     * GCM request mapping URL path.
     */
    val gcmRequestMapping = "/gcm-encryption/v1"

    private fun httpBasic(): RequestPostProcessor {
        return SecurityMockMvcRequestPostProcessors.httpBasic(
                this.userConfigProperties.name,
                this.userConfigProperties.password)
    }

    @Test
    fun test_whenEncryptCalled_shouldReturnEncryptedText() {
        // given
        val get = get("$gcmRequestMapping/encrypt").with(httpBasic())
        get.param("text", "some_text_to_be_encrypted")

        // when
        val resultActions = mockMvc.perform(get).andDo(print())

        // then
        resultActions.andExpect(status().isOk)
        resultActions.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        resultActions.andExpect(jsonPath("$").isNotEmpty)
    }

    @Test
    fun test_whenDecryptCalled_shouldReturnPlainText() {
        // given
        val get = get("$gcmRequestMapping/decrypt").with(httpBasic())
        get.param("secret", "AQm+vvOG0qEqtLDvNkxMp1AIpAq+ijQB+ordTp/QOHxGv5NDzx4MjtzGTPcPvmPwXLNTlMCQT3zCvw==~JEeZzey9B64+BV5J")

        // when
        val resultActions = mockMvc.perform(get).andDo(print())

        // then
        resultActions.andExpect(status().isOk);
        resultActions.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        resultActions.andExpect(jsonPath("$").isNotEmpty)
        resultActions.andExpect(jsonPath("$", equalTo("some_text_to_be_encrypted")))
    }

    @Test
    fun test_whenDecryptCalled_shouldOccurInternalServerError() {
        // given
        val get = get("$gcmRequestMapping/decrypt").with(httpBasic())
        get.param("secret", "some_text_to_be_decrypted")

        // when
        val resultActions = mockMvc.perform(get).andDo(print())

        // then
        resultActions.andExpect(status().isInternalServerError)
        resultActions.andExpect(jsonPath("$", equalTo("An error occurred while trying to decrypt")));
    }

    @Test
    fun test_whenDecryptWithBase64Called_shouldOccurInternalServerError() {
        // given
        val get = get("$gcmRequestMapping/decrypt").with(httpBasic());
        get.param("secret", Base64.getEncoder().encodeToString("some_text_to_be_decrypted".toByteArray(Charsets.UTF_8)));

        // when
        val resultActions = mockMvc.perform(get).andDo(print());

        // then
        resultActions.andExpect(status().isInternalServerError);
        resultActions.andExpect(jsonPath("$", equalTo("An error occurred while trying to decrypt")));
    }

    @Test
    fun test_whenDecryptWithBase64WithIVCalled_shouldOccurInternalServerError() {
        // given
        val cipherText = Base64.getEncoder().encodeToString("some_text_to_be_decrypted".toByteArray(Charsets.UTF_8));
        val iv = Base64.getEncoder().encodeToString("some_iv".toByteArray(Charsets.UTF_8));
        val get = get("$gcmRequestMapping/decrypt").with(httpBasic());
        get.param("secret", "$cipherText~$iv");

        // when
        val resultActions = mockMvc.perform(get).andDo(print());

        // then
        resultActions.andExpect(status().isInternalServerError);
        resultActions.andExpect(jsonPath("$", equalTo("An error occurred while trying to decrypt")));
    }
}
