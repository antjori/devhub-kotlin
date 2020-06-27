package pt.devhub.antjori.api.encryption.gcm.service

/**
 * The encryption service interface.
 *
 * @author antjori
 */
interface GCMEncryptionService {

    /**
     * Encrypts the given `text`.
     *
     * @param text the {@see String} to encrypt
     * @return A {@see String} representation of the encrypted `text`.
     */
    fun encrypt(text: String): String

    /**
     * Decrypts the given `secret`.
     *
     * @param secret the {@see String} to decrypt
     * @return A {@see String} representation of the decrypted `secret`.
     */
    fun decrypt(secret: String): String
}