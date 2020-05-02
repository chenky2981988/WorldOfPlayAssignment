package com.chirag.worldofplayassignment.ui.dashboard.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chirag.worldofplayassignment.data.model.StoryDetails


/**
 * Created by Chirag Sidhiwala on 26/4/20.
 */
class DashboardDetailsVewModel : ViewModel() {

    private var storyDetails = MutableLiveData<StoryDetails>()

    init {
        storyDetails.value = StoryDetails()
    }

    fun setStoryDetails(story: StoryDetails) {
        storyDetails.value = story
    }

}