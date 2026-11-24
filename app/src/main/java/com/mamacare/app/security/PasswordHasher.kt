package com.mamacare.app.security

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

data class PasswordData(val hash: String, val salt: String)

object PasswordHasher {
    private const val ALGORITHM = "PBKDF2WithHmacSHA256"
    private const val ITERATIONS = 120_000
    private const val KEY_LENGTH = 256
    private const val SALT_LENGTH = 16

    fun hash(password: String): PasswordData {
        val saltBytes = ByteArray(SALT_LENGTH).also(SecureRandom()::nextBytes)
        val salt = Base64.encodeToString(saltBytes, Base64.NO_WRAP)
        val hash = derive(password, saltBytes)
        return PasswordData(hash, salt)
    }

    fun verify(password: String, storedSalt: String, storedHash: String): Boolean {
        return try {
            val saltBytes = Base64.decode(storedSalt, Base64.NO_WRAP)
            val calculated = derive(password, saltBytes)
            MessageDigest.isEqual(
                Base64.decode(storedHash, Base64.NO_WRAP),
                Base64.decode(calculated, Base64.NO_WRAP)
            )
        } catch (_: Exception) {
            false
        }
    }

    private fun derive(password: String, salt: ByteArray): String {
        val spec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val key = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec)
        spec.clearPassword()
        return Base64.encodeToString(key.encoded, Base64.NO_WRAP)
    }
}
