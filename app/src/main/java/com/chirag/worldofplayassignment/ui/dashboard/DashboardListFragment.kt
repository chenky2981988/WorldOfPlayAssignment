package com.chirag.worldofplayassignment.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chirag.worldofplayassignment.R
import com.chirag.worldofplayassignment.data.model.NetworkState
import com.chirag.worldofplayassignment.data.model.ResourceState
import com.chirag.worldofplayassignment.data.model.StoryDetails
import com.chirag.worldofplayassignment.ui.dashboard.adapter.DashboardListAdapter
import com.chirag.worldofplayassignment.ui.dashboard.viewmodels.DashboardListViewModel
import com.chirag.worldofplayassignment.ui.dashboard.viewmodels.DashboardViewModelFactory
import kotlinx.android.synthetic.main.dashboard_list_fragment.*

class DashboardListFragment : Fragment() {

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
        val view =
            LayoutInflater.from(context).inflate(R.layout.dashboard_list_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            DashboardViewModelFactory()
        ).get(DashboardListViewModel::class.java)
        observeLiveData()
        initializeList()
        initSwipeRefreshLayout()
    }

    private fun observeLiveData() {
        //observe live data emitted by view model
        viewModel.getStoryDetailsList().observe(viewLifecycleOwner, Observer {
            dashboardListAdapter.submitList(it)
        })

        viewModel.getNetworkState().observe(viewLifecycleOwner, Observer { networkState ->
            if (networkState.status == ResourceState.LOADING) {
                storyProgressBar.visibility = View.VISIBLE
            } else if (networkState.status == ResourceState.MORE_LOADING) {
                storyProgressBar.visibility = View.INVISIBLE
                Toast.makeText(context, "Loading more stories...", Toast.LENGTH_SHORT).show()
            } else if (networkState.status == ResourceState.SUCCESS) {
                storyProgressBar.visibility = View.INVISIBLE
            } else if (networkState.status == ResourceState.ERROR) {
                storyProgressBar.visibility = View.INVISIBLE
                Toast.makeText(context, networkState.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun initializeList() {
        topStoriesList.layoutManager = LinearLayoutManager(context)
        dashboardListAdapter = context?.let {
            DashboardListAdapter(it) { storyDetails ->
                callDetailFragment(storyDetails)
            }
        }!!
        topStoriesList.adapter = dashboardListAdapter
    }


    /**
     * Initialize the swipe refresh layout
     */
    private fun initSwipeRefreshLayout() {
        viewModel.getNetworkState().observe(viewLifecycleOwner, Observer { networkState ->

            if (dashboardListAdapter.currentList != null) {
                if (dashboardListAdapter.currentList!!.size > 0) {
                    swipeToRefreshLayout.isRefreshing =
                        networkState?.status == NetworkState.LOADING.status
                    storyProgressBar.visibility = View.INVISIBLE
                } else {
                    swipeToRefreshLayout.isEnabled = networkState?.status == ResourceState.SUCCESS
                }
            } else {
                swipeToRefreshLayout.isEnabled = networkState?.status == ResourceState.SUCCESS
            }
        })

        swipeToRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun callDetailFragment(storyDetails: StoryDetails) {
        activity!!.supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.container, DashboardDetailsFragment.newInstance(storyDetails))
            .addToBackStack(DashboardDetailsFragment::class.java.simpleName)
            .commit()
    }

}
