package com.chirag.worldofplayassignment.data

import androidx.lifecycle.MutableLiveData


/**
 * Created by Chirag Sidhiwala on 24/4/20.
 */
class DashboardRepository(val dashboardDataSource: DashboardDataSource) {
    var topStoriesLiveData: MutableLiveData<List<String>>
    var topStories: List<String>? =null
        private set
    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        topStoriesLiveData = MutableLiveData()
        topStories = emptyList()
    }

    fun getTopStoriesIdList(): MutableLiveData<List<String>> {
        var resultOutput = emptyList<String>()
        dashboardDataSource.getStoryIdList() { result ->
            if (result is Result.Success) {
                setTopStories(result.data)
                resultOutput = result.data
                topStoriesLiveData.value = resultOutput
            }
        }
        return topStoriesLiveData
    }

    private fun setTopStories(stories: List<String>) {
        this.topStories = stories
    }
}