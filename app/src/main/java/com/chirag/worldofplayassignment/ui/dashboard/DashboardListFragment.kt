package com.chirag.worldofplayassignment.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chirag.worldofplayassignment.R
import com.chirag.worldofplayassignment.data.model.StoryDetails
import com.chirag.worldofplayassignment.databinding.DashboardListFragmentBinding
import com.chirag.worldofplayassignment.ui.dashboard.adapter.DashboardListAdapter

class DashboardListFragment : Fragment() {

    private lateinit var binding: DashboardListFragmentBinding
    private lateinit var viewModel: DashboardListViewModel
    private lateinit var dashboardListAdapter: DashboardListAdapter

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
            DashboardViewModelFactory(viewLifecycleOwner)
        ).get(DashboardListViewModel::class.java)
        binding.dashboardListViewModel = viewModel
        binding.storyProgressBar.visibility = View.VISIBLE
        observeLiveData()
        initializeList()
        /*viewModel.getTopStoriesId().observe(viewLifecycleOwner, Observer { list ->
            Log.d("TAG","Total Story Ids = ${list.size}")
            viewModel.setProgress(View.INVISIBLE)
            binding.storyProgressBar.visibility = View.INVISIBLE
            getStoriesDetailsList()
        })*/

    }

    private fun observeLiveData() {
        //observe live data emitted by view model
        viewModel.getStoryDetailsList().observe(viewLifecycleOwner, Observer {
            binding.storyProgressBar.visibility = View.INVISIBLE
            dashboardListAdapter.submitList(it)
        })
    }

    private fun initializeList() {
        binding.topStoriesList.layoutManager = LinearLayoutManager(context)
        dashboardListAdapter = context?.let {
            DashboardListAdapter(it) { storyDetails ->
                callDetailFragment(storyDetails)
            }
        }!!
        binding.topStoriesList.adapter = dashboardListAdapter
    }

    private fun callDetailFragment(storyDetails: StoryDetails) {
        activity!!.supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.container, DashboardDetailsFragment.newInstance(storyDetails))
            .addToBackStack(DashboardDetailsFragment.javaClass.simpleName)
            .commit()
    }

}
