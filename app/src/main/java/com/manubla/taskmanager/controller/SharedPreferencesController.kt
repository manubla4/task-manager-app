package com.manubla.taskmanager.controller

import android.content.Context
import android.preference.PreferenceManager

object SharedPreferencesController {

    private const val accessTokenKey = "AccessToken"

    fun getToken(context: Context?): String? =
        context?.let {
            PreferenceManager
            .getDefaultSharedPreferences(it)
            .getString(accessTokenKey, null)
        }


    fun saveToken(authToken: String, context: Context?) {
        context?.let {
            PreferenceManager
                .getDefaultSharedPreferences(it)
                .edit()
                .putString(accessTokenKey, authToken)
                .apply()
        }
    }

    fun removeToken(context: Context?) {
        context?.let {
            PreferenceManager
                .getDefaultSharedPreferences(it)
                .edit()
                .remove(accessTokenKey)
                .apply()
        }
    }
}