package com.chirag.worldofplayassignment

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


/**
 * Created by Chirag Sidhiwala on 24/4/20.
 */
class AssignmentApplication: Application() {
    companion object {
        //lateinit var sharedPreferences: SharedPrefs
        lateinit var requestQueue: RequestQueue
    }

    override fun onCreate() {
        //sharedPreferences = SharedPrefs(applicationContext)
        super.onCreate()
        requestQueue = Volley.newRequestQueue(this)
    }
}