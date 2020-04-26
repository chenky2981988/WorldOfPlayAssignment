package com.chirag.worldofplayassignment.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.chirag.worldofplayassignment.R
import com.chirag.worldofplayassignment.data.model.StoryDetails
import com.chirag.worldofplayassignment.databinding.FragmentDashboardDetailsBinding

/**
 * A simple [Fragment] subclass.
 */
class DashboardDetailsFragment : Fragment() {

    lateinit var binding: FragmentDashboardDetailsBinding
    lateinit var viewModel: DashboardDetailsVewModel
    private lateinit var storyDetails: StoryDetails

    companion object {
        fun newInstance(story: StoryDetails) =
            DashboardDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("storyDetails", story)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard_details, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            DashboardDetailsViewModelFactory()
        ).get(DashboardDetailsVewModel::class.java)
        binding.dashboardDetailsViewModel = viewModel

        storyDetails = arguments!!.getParcelable<StoryDetails>("storyDetails")!!
        viewModel.setStoryDetails(storyDetails)
        binding.storyUrl.loadUrl(storyDetails.url)
    }

}
