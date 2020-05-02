package com.chirag.worldofplayassignment.ui.dashboard

import android.os.Bundle
import android.view.View
import com.chirag.worldofplayassignment.R
import com.chirag.worldofplayassignment.ui.BaseActivity

class DashboardActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getSavedTheme())
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
