package com.chirag.worldofplayassignment.data.model


/**
 * Created by Chirag Sidhiwala on 24/4/20.
 */
data class StoryDetails(
    val by: String,
    val descendants: Int,
    val id: Long,
    val kids: List<Long>,
    val score: Int,
    val time: String,
    val title: String,
    val type: String,
    val url: String
)