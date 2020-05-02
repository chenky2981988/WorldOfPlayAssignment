package com.chirag.worldofplayassignment.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.chirag.worldofplayassignment.AssignmentApplication
import com.chirag.worldofplayassignment.R


/**
 * Created by Chirag Sidhiwala on 27/4/20.
 */
abstract class BaseActivity : AppCompatActivity() {
    companion object {
        val THEME_1 = "theme1"
        val THEME_2 = "theme2"
    }

    var currentTheme: String

    init {
        currentTheme = ""
    }
    fun saveTheme(value: String) {
        AssignmentApplication.sharedPreferences.theme = value
        recreate()
    }

    fun getSavedTheme(): Int {
        val theme: String =
            AssignmentApplication.sharedPreferences.theme
        currentTheme = theme
        if (theme == THEME_1)
            return R.style.BaseTheme_Theme1
        else
            return R.style.BaseTheme_Theme2
    }

}