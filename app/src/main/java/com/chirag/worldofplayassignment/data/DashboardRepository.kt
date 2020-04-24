package com.chirag.worldofplayassignment.data

import com.chirag.worldofplayassignment.data.model.LoggedInUser
import com.chirag.worldofplayassignment.data.model.TopStories


/**
 * Created by Chirag Sidhiwala on 24/4/20.
 */
class DashboardRepository(val dashboardDataSource: DashboardDataSource) {
    var topStories: TopStories? = null
        private set

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        topStories = null
    }

    fun getTopStories(): Result<TopStories> {
        val result = dashboardDataSource.getTopStories()
        if (result is Result.Success) {
            val storyIdList = result.data
        }
        return result
    }

    private fun setTopStories(stories: TopStories){
        this.topStories = stories
    }
}