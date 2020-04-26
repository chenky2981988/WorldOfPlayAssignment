package com.chirag.worldofplayassignment.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.chirag.worldofplayassignment.R

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DashboardListFragment.newInstance())
                .commitNow()
        }
    }

    fun closeBtnClicked(view: View) {
        onBackPressed()
    }
}
