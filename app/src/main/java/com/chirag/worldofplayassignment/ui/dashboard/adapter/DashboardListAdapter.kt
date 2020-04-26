package com.chirag.worldofplayassignment.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chirag.worldofplayassignment.R
import com.chirag.worldofplayassignment.data.model.StoryDetails
import com.chirag.worldofplayassignment.utils.DiffUtilCallBack


/**
 * Created by Chirag Sidhiwala on 25/4/20.
 */
class DashboardListAdapter(val context: Context, val itemClicked: (StoryDetails) -> Unit) :
    PagedListAdapter<StoryDetails, DashboardListAdapter.DashboardListViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardListViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.top_stories_list_item, parent, false)
        return DashboardListViewHolder(view, itemClicked)
    }

//    override fun getItemCount(): Int {
//        return storyList.size
//    }

    override fun onBindViewHolder(holder: DashboardListViewHolder, position: Int) {
        getItem(position)?.let { holder.bindStory(context, it) }
    }

    inner class DashboardListViewHolder(itemView: View, val itemClicked: (StoryDetails) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val storyTitle = itemView?.findViewById<TextView>(R.id.storyTitle)
        val storySubTitle = itemView?.findViewById<TextView>(R.id.storySubTitle)
        val favoriteToggleBtn = itemView?.findViewById<ToggleButton>(R.id.favToggleBtn)

        fun bindStory(context: Context, storyDetails: StoryDetails) {
            with(storyDetails) {
                storyTitle.text = storyDetails.title
                storySubTitle.text = storyDetails.id.toString()

                itemView.setOnClickListener { itemClicked(storyDetails) }
            }
        }
    }
}