package com.chirag.worldofplayassignment.utils

import androidx.recyclerview.widget.DiffUtil
import com.chirag.worldofplayassignment.data.model.StoryDetails


/**
 * Created by Chirag Sidhiwala on 26/4/20.
 */
class DiffUtilCallBack : DiffUtil.ItemCallback<StoryDetails>() {
    override fun areItemsTheSame(oldItem: StoryDetails, newItem: StoryDetails): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StoryDetails, newItem: StoryDetails): Boolean {
        return oldItem.title == newItem.title
                && oldItem.score == newItem.score
    }
}