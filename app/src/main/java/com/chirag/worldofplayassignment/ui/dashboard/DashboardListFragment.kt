package com.chirag.worldofplayassignment.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chirag.worldofplayassignment.R
import com.chirag.worldofplayassignment.databinding.DashboardListFragmentBinding

class DashboardListFragment : Fragment() {

    private lateinit var binding: DashboardListFragmentBinding
    private lateinit var viewModel: DashboardListViewModel

    companion object {
        fun newInstance() =
            DashboardListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dashboard_list_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            DashboardViewModelFactory()
        ).get(DashboardListViewModel::class.java)
        binding.dashboardListViewModel = viewModel
        binding.storyProgressBar.visibility = View.VISIBLE
        viewModel.getTopStoriesId().observe(viewLifecycleOwner, Observer { list ->
            Log.d("TAG","Total Story Ids = ${list.size}")
            viewModel.setProgress(View.INVISIBLE)
            binding.storyProgressBar.visibility = View.INVISIBLE
        })

//        viewModel.topStoryIdList.observe(viewLifecycleOwner, Observer { list ->
//            Log.d("TAG","Total Story Ids = ${list.size}")
//        })
    }

}
