package com.fov.core.security.encryption

import android.security.keystore.KeyProperties
import java.security.SecureRandom
import android.util.*
import com.fov.core.di.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import android.security.keystore.KeyGenParameterSpec
import java.security.KeyStore


class KeyGeneration constructor(
    private val sharedPref: Preferences
) {

    @Throws(Exception::class)
    fun generateSecretKey(algorithm  : String =  "AES"): SecretKey? {
        val secureRandom = SecureRandom()
        val keyGenerator = KeyGenerator.getInstance(algorithm)
        //generate a key with secure random
        keyGenerator?.init(128, secureRandom)
        return keyGenerator?.generateKey()
    }
    fun getKeyFromKeyStore() : SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        val secretKeyEntry = keyStore
            .getEntry(ALIAS, null) as KeyStore.SecretKeyEntry

        return secretKeyEntry.secretKey
    }
    fun generateSecretKeyIntoKeyStore() : SecretKey{
        val keyGenerator = KeyGenerator
            .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
             ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        keyGenerator.init(keyGenParameterSpec);
        val secretKey = keyGenerator.generateKey();

        return secretKey
    }

    fun saveSecretKey(secretKey: SecretKey,scope: CoroutineScope) {
        scope.launch {
            val encodedKey = Base64.encodeToString(secretKey.encoded, Base64.DEFAULT)
            sharedPref.setEncryptionKey(encodedKey)
        }
    }
    companion object {
        val ALIAS = "userKey"
        fun keyToString(secretKey: SecretKey): String? {
            /* Get key in encoding format */
            val encoded = secretKey.encoded

            /*
       * Encodes the specified byte array into a String using Base64 encoding
       * scheme
       */
            return Base64.encodeToString(encoded, Base64.DEFAULT)
        }

        fun decodeKeyFromString(keyStr: String?): SecretKey? {
            /* Decodes a Base64 encoded String into a byte array */
            val decodedKey: ByteArray = Base64.decode(keyStr, Base64.DEFAULT)

            /* Constructs a secret key from the given byte array */
            return SecretKeySpec(
                decodedKey, 0,
                decodedKey.size, "AES"
            )
        }
    }
}