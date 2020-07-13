package pt.devhub.antjori.api.encryption.gcm.service.impl

import com.google.crypto.tink.Aead
import com.google.crypto.tink.CleartextKeysetHandle
import com.google.crypto.tink.JsonKeysetReader
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import org.apache.commons.lang3.CharSet
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import pt.devhub.antjori.api.encryption.gcm.service.GCMEncryptionService
import java.nio.charset.Charset
import java.security.SecureRandom
import java.util.Base64
import javax.annotation.PostConstruct

/**
 * The encryption service implementation.
 *
 * @author antjori
 */
@Service
class GCMEncryptionServiceImpl(
        @Value("\${gcm-encryption.keysetfile}") val keysetFile: Resource,
        @Value("\${gcm-encryption.secure-random-instance}") val secureRandomInstance: String,
        @Value("\${gcm-encryption.iv-size}") val ivSize: Int,
        @Value("\${gcm-encryption.separator}") val separator: String
) : GCMEncryptionService {

    private val keysetHandle: KeysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withFile(keysetFile.getFile()))

    private val secureRandom: SecureRandom = SecureRandom.getInstance(secureRandomInstance)

    @PostConstruct
    fun init() {
        AeadConfig.register()
    }

    /**
     * Encrypts the given {@code text}.
     *
     * @param text the {@see String} to encrypt
     * @return A {@see String} representation of the encrypted {@code text}.
     */
    override fun encrypt(text: String): String {
        // 1. Attain the encryption primitive
        val aead = keysetHandle.getPrimitive(Aead::class.java)

        // 2. Generate initialization vector
        val iv = ByteArray(ivSize)
        secureRandom.nextBytes(iv)

        // 3. Use the primitive to encrypt a plaintext
        val cipherText = aead.encrypt(text.toByteArray(), iv)

        // 4. Concat Base64 encoded cipherText, with separator and with Base64 encoded IV
        return Base64.getEncoder().encodeToString(cipherText) + separator + Base64.getMimeEncoder().encodeToString(iv)
    }

    /**
     * Decrypts the given {@code secret}.
     *
     * @param secret the {@see String} to decrypt
     * @return A {@see String} representation of the decrypted {@code secret}.
     */
    override fun decrypt(secret: String): String {
        // 1. Attain the secret parts, cipherText and IV, and Base64 decode them
        val secretParts = secret.split(separator);
        val cipherText = Base64.getDecoder().decode(secretParts[0]);
        val iv = Base64.getDecoder().decode(secretParts[1]);

        // 2. Attain the encryption primitive
        val aead = keysetHandle.getPrimitive(Aead::class.java);

        // 3. Use the primitive to decrypt the ciphered text
        val text = aead.decrypt(cipherText, iv);

        return String(text);
    }
}
