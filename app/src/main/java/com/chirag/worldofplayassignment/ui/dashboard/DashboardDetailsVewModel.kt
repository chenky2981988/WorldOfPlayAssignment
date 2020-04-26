package com.chirag.worldofplayassignment.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chirag.worldofplayassignment.data.model.StoryDetails


/**
 * Created by Chirag Sidhiwala on 26/4/20.
 */
class DashboardDetailsVewModel: ViewModel() {

    private var storyDetails = MutableLiveData<StoryDetails> ()
    val storyLiveData: LiveData<StoryDetails>
        get() {
            return storyDetails
        }


    init {
        storyDetails.value = StoryDetails()
    }

    fun setStoryDetails(story: StoryDetails){
        storyDetails.value = story
    }

}