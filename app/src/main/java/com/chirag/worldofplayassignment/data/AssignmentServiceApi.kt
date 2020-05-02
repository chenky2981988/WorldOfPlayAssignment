package com.chirag.worldofplayassignment.data

import com.chirag.worldofplayassignment.data.model.StoryDetails
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by Chirag Sidhiwala on 26/4/20.
 */
interface AssignmentServiceApi {

    @GET("v0/topstories.json")
    fun getTopStories(): Single<List<Long>>

    @GET("/v0/item/{story_id}.json")
    fun getStoryDetails(@Path("story_id") id: String): Single<StoryDetails>

    /**
     * Companion object to create the AssignmentServiceApi
     */
    companion object Factory {
        fun create(): AssignmentServiceApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://hacker-news.firebaseio.com/")
                .build()

            return retrofit.create(AssignmentServiceApi::class.java);
        }
    }
}