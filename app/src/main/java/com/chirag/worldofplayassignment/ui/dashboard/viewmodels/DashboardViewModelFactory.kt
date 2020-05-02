package com.chirag.worldofplayassignment.ui.dashboard.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chirag.worldofplayassignment.data.AssignmentServiceApi
import com.chirag.worldofplayassignment.data.DashboardRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Chirag Sidhiwala on 24/4/20.
 */
class DashboardViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardListViewModel::class.java)) {
            return DashboardListViewModel(
                dashboardRepository = DashboardRepository(
                    compositeDisposable = CompositeDisposable(),
                    assignmentServiceApi = AssignmentServiceApi.create()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}