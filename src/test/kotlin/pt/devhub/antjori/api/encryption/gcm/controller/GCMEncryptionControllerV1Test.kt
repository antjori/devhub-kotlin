package pt.devhub.antjori.api.encryption.gcm.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import pt.devhub.antjori.api.encryption.gcm.service.GCMEncryptionService

/**
 * Test class for {@link GCMEncryptionControllerV1} where will be depicted the unit tests.
 *
 * @author antjori
 */
@ExtendWith(MockitoExtension::class)
class GCMEncryptionControllerV1Test {

    @InjectMocks
    lateinit var gcmEncryptionController: GCMEncryptionControllerV1

    @Mock
    lateinit var gcmEncryptionService: GCMEncryptionService

    companion object {
        @BeforeAll
        @JvmStatic
        private fun init() {
            MockitoAnnotations.initMocks(GCMEncryptionControllerV1Test::class)
        }
    }

    // =============
    // encrypt tests
    // =============

    @Test
    fun test_encrypt() {
        // given
        `when`(gcmEncryptionService.encrypt(anyString())).thenReturn("some_encrypted_text")

        // when
        val responseEntity = gcmEncryptionController.encrypt("some_text")

        // then
        verify(gcmEncryptionService, times(1)).encrypt(anyString())

        assertNotNull(responseEntity)
        assertNotNull(responseEntity.statusCode)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
        assertEquals("some_encrypted_text", responseEntity.body)
    }

    @Test
    fun test_encrypt_willThrowException () {
        // given
        `when`(gcmEncryptionService.encrypt(anyString())).thenThrow(RuntimeException::class.java)

        // when
        val exception = assertThrows(RuntimeException::class.java, { gcmEncryptionService.encrypt("some_text")},
        "Expected encrypt to throw Exception, but it didn't")

        // then
        assertNotNull(exception)
    }

    // =============
    // decrypt tests
    // =============

    @Test
    fun test_decrypt() {
        // given
        `when`(gcmEncryptionService.decrypt(anyString())).thenReturn("some_text")

        // when
        val responseEntity = gcmEncryptionController.decrypt("some_text_to_decrypt")

        // then
        assertNotNull(responseEntity)
        assertNotNull(responseEntity.statusCode)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
        assertEquals("some_text", responseEntity.body)
    }

    @Test
    fun test_decrypt_willThrowException() {
        // given
        `when`(gcmEncryptionService.encrypt(anyString())).thenThrow(RuntimeException::class.java)

        // when
        val exception = assertThrows(RuntimeException::class.java, { gcmEncryptionService.encrypt("some_text")},
        "Expected decrypt to throw Exception, but it didn't")

        // then
        assertNotNull(exception)
    }
}
