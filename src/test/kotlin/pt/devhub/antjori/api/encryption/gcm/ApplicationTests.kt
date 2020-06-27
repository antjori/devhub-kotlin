package pt.devhub.antjori.api.encryption.gcm

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import pt.devhub.antjori.api.encryption.Application

@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class ApplicationTests(@Autowired private val mockMvc: MockMvc) {

	/**
	 * Common request mapping URL path.
	 */
	val REQUEST_MAPPING: String = "/encryption/v1"

	@Test
	fun whenEncryptCalled_shouldReturnEncryptedText() {
	}

	@Test
	fun whenDencryptCalled_shouldReturnPlainText() {
	}
}
