package com.chirag.worldofplayassignment.ui.dashboard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.chirag.worldofplayassignment.data.DashboardRepository
import com.chirag.worldofplayassignment.data.model.NetworkState
import com.chirag.worldofplayassignment.data.model.StoryDetails

class DashboardListViewModel(private val dashboardRepository: DashboardRepository) : ViewModel() {
    fun getStoryDetailsList(): LiveData<PagedList<StoryDetails>> {
        dashboardRepository.getStoriesList()
        return dashboardRepository.getStoryListLiveData()
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return dashboardRepository.getNetworkState()
    }

    fun refresh() {
        dashboardRepository.refreshStories()
    }
}
