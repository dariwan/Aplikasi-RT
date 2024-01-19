package com.rt04.myapplication.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.rt04.myapplication.core.data.Email
import com.rt04.myapplication.core.data.Role
import com.rt04.myapplication.core.data.Username
import com.rt04.myapplication.core.utils.Constant
import com.rt04.myapplication.core.utils.Constant.KEY_EMAIL
import com.rt04.myapplication.core.utils.Constant.KEY_IS_LOGIN
import com.rt04.myapplication.core.utils.Constant.KEY_NAME
import com.rt04.myapplication.core.utils.Constant.KEY_ROLE
import com.rt04.myapplication.core.utils.Constant.KEY_TOKEN
import com.rt04.myapplication.core.utils.Constant.PREFS_NAME

class SessionManager(context: Context) {
    private var sharedPref: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val editor = sharedPref.edit()

    fun setBooleanPref(prefBoolean: String, value: Boolean) {
        editor.putBoolean(prefBoolean, value)
        editor.apply()
    }

    fun setStringPref(prefString: String, value: String) {
        editor.putString(prefString, value)
        editor.apply()
    }

    fun clearData() {
        editor.clear().apply()
    }

    val isLogin = sharedPref.getBoolean(KEY_IS_LOGIN, false)
    val getToken = sharedPref.getString(KEY_TOKEN, "")
    val getUsername = sharedPref.getString(KEY_NAME, "")
    val getEmail = sharedPref.getString(KEY_EMAIL, "")
    val getRole = sharedPref.getString(KEY_ROLE, "")

    fun getRole(): Role {
        val role = sharedPref.getString(KEY_ROLE, "")
        return Role(role)
    }

    fun getUsername(): Username {
        val username = sharedPref.getString(KEY_NAME, "")
        return Username(username)
    }

    fun getEmail(): Email {
        val token = sharedPref.getString(KEY_EMAIL, "")
        return Email(token)
    }
}