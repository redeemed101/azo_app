package com.fov.core.security.encryption

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Encryption constructor(){



    @Throws(Exception::class)
    fun encrypt(key: SecretKey, fileData: ByteArray): ByteArray {
        val data = key.encoded
        val sKeySpec = SecretKeySpec(data, 0, data.size, "AES")
        val cipher = Cipher.getInstance("AES", "BC")
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, IvParameterSpec(ByteArray(cipher.blockSize)))
        return cipher.doFinal(fileData)
    }
    @Throws(Exception::class)
    fun encrypt(keyString: String, fileData: ByteArray): ByteArray {
        val key = KeyGeneration.decodeKeyFromString(keyString)
        if(key != null)
        return encrypt(key,fileData)
        else
            throw Exception("failed to retrieve key")
    }

    @Throws(Exception::class)
    fun decrypt(key: SecretKey, fileData: ByteArray): ByteArray {
        val decrypted: ByteArray
        val cipher = Cipher.getInstance("AES", "BC")
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(ByteArray(cipher.blockSize)))
        decrypted = cipher.doFinal(fileData)
        return decrypted
    }
    fun decrypt(keyString: String, fileData: ByteArray): ByteArray {
        val key = KeyGeneration.decodeKeyFromString(keyString)
        if(key != null)
            return decrypt(key,fileData)
        else
            throw Exception("failed to retrieve key")
    }

}