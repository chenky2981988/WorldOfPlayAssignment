package com.chirag.worldofplayassignment.data

import com.chirag.worldofplayassignment.data.model.StoryDetails
import com.chirag.worldofplayassignment.data.model.TopStories
import java.io.IOException


/**
 * Created by Chirag Sidhiwala on 24/4/20.
 */
class DashboardDataSource {
    fun getTopStories(): Result<TopStories>{
        try {
            return Result.Success(TopStories(listOf("22966332", "22960225", "22965049")))
        } catch (e: Throwable) {
            e.printStackTrace()
            return Result.Error(IOException("Error in getting stroy list", e))
        }
    }

    fun getStoryDetails(id:String): Result<StoryDetails> {
        try {
            return Result.Success(
                StoryDetails(
                    "based2",
                    28,
                    4,
                    emptyList(),
                    4,
                    "1587494322",
                    "YouTube sued by Ripple over scam videos, in major challenge to tech giant",
                    "story",
                    "https://fortune.com/2020/04/21/ripple-sues-youtube-scam-videos-crypto-garlinghouse/"
                )
            )
        }catch (e: Throwable) {
            e.printStackTrace()
            return Result.Error(IOException("Error in getting stroy details", e))
        }
    }
}