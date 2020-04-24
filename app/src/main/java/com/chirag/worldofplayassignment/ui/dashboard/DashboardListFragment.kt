package com.chirag.worldofplayassignment.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.chirag.worldofplayassignment.R

class DashboardListFragment : Fragment() {

    companion object {
        fun newInstance() =
            DashboardListFragment()
    }

    private lateinit var viewModel: DashboardListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dashboard_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DashboardListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
