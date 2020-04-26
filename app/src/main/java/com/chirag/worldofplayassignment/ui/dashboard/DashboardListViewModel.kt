package com.chirag.worldofplayassignment.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.chirag.worldofplayassignment.data.DashboardRepository
import com.chirag.worldofplayassignment.data.model.Resource
import com.chirag.worldofplayassignment.data.model.StoryDetails

class DashboardListViewModel(private val dashboardRepository: DashboardRepository) : ViewModel() {
    private val topStoriesList = MutableLiveData<PagedList<StoryDetails>>()
    val topStories: LiveData<PagedList<StoryDetails>> = topStoriesList
    private var inProgress = MutableLiveData<Int>()
    val isInProgress : LiveData<Int>
        get() = inProgress

//    fun getTopStoriesId(): MutableLiveData<List<String>> {
//        return dashboardRepository.getTopStoriesIdList()
//    }

    fun getStoryDetailsList(): LiveData<PagedList<StoryDetails>> {
        dashboardRepository.getStoriesList()
        return dashboardRepository.getStoryListLiveData()
    }

    fun setProgress(isProgress: Int) {
        inProgress.value = isProgress
    }
}
