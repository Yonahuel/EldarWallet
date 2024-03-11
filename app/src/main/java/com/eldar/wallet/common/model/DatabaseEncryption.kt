package com.eldar.wallet.common.model

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.security.keystore.KeyProtection
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 * Clase para gestionar el cifrado y descifrado de datos utilizando el Android Keystore.
 */
class DatabaseEncryption {
    private val keyStore: KeyStore

    init {
        keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
    }

    /**
     * Genera una clave secreta utilizando el algoritmo AES y la almacena en el Android Keystore.
     *
     * @param alias Alias de la clave.
     * @return [SecretKey] generada o null si ya existe una clave con el mismo alias.
     */
    private fun generateKey(alias: String): SecretKey? {
        val key = getKey(alias)

        if (key == null) {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .build()
            keyGenerator.init(keyGenParameterSpec)
            return keyGenerator.generateKey()
        } else {
            return null
        }
    }

    /**
     * Guarda una clave secreta en el Android Keystore.
     *
     * @param alias Alias de la clave.
     */
    fun saveKey(alias: String) {
        val key = generateKey("qr_key")
        val keyProtection = KeyProtection.Builder(KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .build()
        val secretKeyEntry = KeyStore.SecretKeyEntry(key)
        keyStore.setEntry(alias, secretKeyEntry, keyProtection)
    }

    /**
     * Obtiene la clave secreta codificada como un array de bytes.
     *
     * @param alias Alias de la clave.
     * @return Array de bytes que representa la clave o null si no se encuentra.
     */
    fun getKey(alias: String): ByteArray? {
        val key = keyStore.getEntry(alias, null) as SecretKey
        return key.encoded
    }
}