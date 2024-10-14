package com.oxio.weather.usecases

import android.content.Context

object PreferencesHelper {
    private const val PREFS_NAME = "weather_app_prefs"
    private const val KEY_FIRST_LAUNCH = "first_launch"

    fun isFirstLaunch(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_FIRST_LAUNCH, true)
    }

    fun setFirstLaunch(context: Context, isFirstLaunch: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_FIRST_LAUNCH, isFirstLaunch).apply()
    }
}
