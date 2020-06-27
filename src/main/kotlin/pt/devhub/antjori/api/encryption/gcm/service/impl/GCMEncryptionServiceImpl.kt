package pt.devhub.antjori.api.encryption.gcm.service.impl

import org.springframework.stereotype.Service
import pt.devhub.antjori.api.encryption.gcm.service.GCMEncryptionService

/**
 * The encryption service implementation.
 *
 * @author antjori
 */
@Service
class GCMEncryptionServiceImpl : GCMEncryptionService {

    /**
     * Encrypts the given {@code text}.
     *
     * @param text the {@see String} to encrypt
     * @return A {@see String} representation of the encrypted {@code text}.
     */
    override fun encrypt(text: String): String {
        TODO("Not yet implemented")
    }

    /**
     * Decrypts the given {@code secret}.
     *
     * @param secret the {@see String} to decrypt
     * @return A {@see String} representation of the decrypted {@code secret}.
     */
    override fun decrypt(secret: String): String {
        TODO("Not yet implemented")
    }
}