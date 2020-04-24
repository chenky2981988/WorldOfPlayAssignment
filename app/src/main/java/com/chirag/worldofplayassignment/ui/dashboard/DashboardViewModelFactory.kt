package com.chirag.worldofplayassignment.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chirag.worldofplayassignment.data.DashboardDataSource
import com.chirag.worldofplayassignment.data.DashboardRepository


/**
 * Created by Chirag Sidhiwala on 24/4/20.
 */
class DashboardViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardListViewModel::class.java)) {
            return DashboardListViewModel(
                dashboardRepository = DashboardRepository(dashboardDataSource = DashboardDataSource())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}