package com.chirag.worldofplayassignment.utils

import android.content.Context
import com.chirag.worldofplayassignment.ui.BaseActivity


/**
 * Created by Chirag Sidhiwala on 27/4/20.
 */
class SharedPrefs(context: Context) {
    val PREFS_FILENAME = "prefs"
    val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    val THEME_KEY = "theme_key"

    var theme : String
        get() = prefs.getString(THEME_KEY, BaseActivity.THEME_1).toString()
        set(value) = prefs.edit().putString(THEME_KEY, value).apply()

}