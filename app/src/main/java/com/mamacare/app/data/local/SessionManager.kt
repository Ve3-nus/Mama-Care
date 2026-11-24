package com.mamacare.app.data.local

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences("mamacare_session", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean = prefs.contains(KEY_USER_ID)

    fun getUserId(): Int = prefs.getInt(KEY_USER_ID, -1)

    fun login(userId: Int, name: String, email: String) {
        prefs.edit()
            .putInt(KEY_USER_ID, userId)
            .putString(KEY_USER_NAME, name)
            .putString(KEY_USER_EMAIL, email)
            .apply()
    }

    fun logout() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
    }
}
