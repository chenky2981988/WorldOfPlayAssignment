package com.chirag.worldofplayassignment.data

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.chirag.worldofplayassignment.AssignmentApplication
import com.chirag.worldofplayassignment.data.model.StoryDetails
import com.google.gson.Gson
import java.io.IOException


/**
 * Created by Chirag Sidhiwala on 24/4/20.
 */
class DashboardDataSource {
    fun getTopStories(): Result<List<String>> {
        try {
            val storyIdList =
                return Result.Success(listOf("22966332", "22960225", "22965049"))
        } catch (e: Throwable) {
            e.printStackTrace()
            return Result.Error(IOException("Error in getting stroy list", e))
        }
    }

    fun getStoryDetails(id: String): Result<StoryDetails> {
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
        } catch (e: Throwable) {
            e.printStackTrace()
            return Result.Error(IOException("Error in getting stroy details", e))
        }
    }

    fun getStoryIdList(complete: (Result<List<String>>) -> Unit) {
        val storyIdListRequest = object : JsonArrayRequest(
            Request.Method.GET,
            "https://hacker-news.firebaseio.com/v0/topstories.json",
            null,
            Response.Listener {
                successResponse ->
                val responseString = successResponse.toString()
                val storiesIdArray =
                    Gson().fromJson(responseString, Array<String>::class.java).toList();
                complete(Result.Success(storiesIdArray))
            },
            Response.ErrorListener {
                complete(Result.Volleyerror(it))
            }) {}
        AssignmentApplication.requestQueue.add(storyIdListRequest)
    }
}