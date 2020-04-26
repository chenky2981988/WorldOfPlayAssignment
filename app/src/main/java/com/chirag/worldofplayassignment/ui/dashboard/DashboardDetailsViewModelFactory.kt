package com.chirag.worldofplayassignment.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * Created by Chirag Sidhiwala on 26/4/20.
 */
class DashboardDetailsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardDetailsVewModel::class.java)) {
            return DashboardDetailsVewModel()
                    as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}