package com.spordestek.app.data.auth

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREF_NAME = "SporDestekPrefs"
    private const val KEY_JWT_TOKEN = "jwt_token"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_ROLE = "user_role"
    
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    
    fun saveToken(context: Context, token: String) {
        getPrefs(context).edit().putString(KEY_JWT_TOKEN, token).apply()
    }
    
    fun getToken(context: Context): String? {
        return getPrefs(context).getString(KEY_JWT_TOKEN, null)
    }
    
    fun saveUserInfo(context: Context, userId: Int, name: String?, email: String?, role: String?) {
        getPrefs(context).edit().apply {
            putInt(KEY_USER_ID, userId)
            putString(KEY_USER_NAME, name)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_ROLE, role)
            apply()
        }
    }
    
    fun getUserId(context: Context): Int {
        return getPrefs(context).getInt(KEY_USER_ID, -1)
    }
    
    fun getUserName(context: Context): String? {
        return getPrefs(context).getString(KEY_USER_NAME, null)
    }
    
    fun getUserEmail(context: Context): String? {
        return getPrefs(context).getString(KEY_USER_EMAIL, null)
    }
    
    fun getUserRole(context: Context): String? {
        return getPrefs(context).getString(KEY_USER_ROLE, null)
    }
    
    fun clearAll(context: Context) {
        getPrefs(context).edit().clear().apply()
    }
    
    fun isLoggedIn(context: Context): Boolean {
        return getToken(context) != null && getUserId(context) != -1
    }
}
