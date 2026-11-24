package com.mamacare.app.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.mamacare.app.data.local.dao.UserDao
import com.mamacare.app.data.local.entity.User
import com.mamacare.app.security.PasswordHasher

sealed class AuthResult {
    data class Success(val user: User) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthRepository(private val userDao: UserDao) {
    suspend fun signUp(name: String, email: String, password: String): AuthResult {
        val normalizedEmail = email.trim().lowercase()
        if (userDao.getByEmail(normalizedEmail) != null) {
            return AuthResult.Error("An account with this email already exists")
        }

        val passwordData = PasswordHasher.hash(password)
        val user = User(
            name = name.trim(),
            email = normalizedEmail,
            passwordHash = passwordData.hash,
            salt = passwordData.salt
        )

        return try {
            val id = userDao.insert(user)
            AuthResult.Success(user.copy(id = id.toInt()))
        } catch (_: SQLiteConstraintException) {
            AuthResult.Error("An account with this email already exists")
        }
    }

    suspend fun login(email: String, password: String): AuthResult {
        val normalizedEmail = email.trim().lowercase()
        val user = userDao.getByEmail(normalizedEmail)
            ?: return AuthResult.Error("Incorrect email or password")

        return if (PasswordHasher.verify(password, user.salt, user.passwordHash)) {
            AuthResult.Success(user)
        } else {
            AuthResult.Error("Incorrect email or password")
        }
    }
}
