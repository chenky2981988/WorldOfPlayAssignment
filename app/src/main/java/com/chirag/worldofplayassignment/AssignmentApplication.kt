package com.chirag.worldofplayassignment

import android.app.Application
import com.chirag.worldofplayassignment.utils.SharedPrefs


/**
 * Created by Chirag Sidhiwala on 24/4/20.
 */
class AssignmentApplication: Application() {

    companion object {
        lateinit var sharedPreferences: SharedPrefs
    }

    override fun onCreate() {
        sharedPreferences = SharedPrefs(applicationContext)
        super.onCreate()

    }
}