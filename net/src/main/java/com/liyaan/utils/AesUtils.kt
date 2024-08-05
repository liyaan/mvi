package com.liyaan.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object AesUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(input: String, key: ByteArray = "1234567812345678".toByteArray()): String {
        val cipher = Cipher.getInstance("AES")
        val secretKeySpec = SecretKeySpec(key, "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val encryptedBytes = cipher.doFinal(input.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(input: String, key: ByteArray = "1234567812345678".toByteArray()): String {
        val cipher = Cipher.getInstance("AES")
        val secretKeySpec = SecretKeySpec(key, "AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input))
        return String(decryptedBytes)
    }
}