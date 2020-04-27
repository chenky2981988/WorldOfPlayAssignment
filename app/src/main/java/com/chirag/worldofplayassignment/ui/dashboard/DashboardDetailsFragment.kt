package com.chirag.worldofplayassignment.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chirag.worldofplayassignment.R
import com.chirag.worldofplayassignment.data.model.StoryDetails
import com.chirag.worldofplayassignment.ui.dashboard.viewmodels.DashboardDetailsVewModel
import com.chirag.worldofplayassignment.ui.dashboard.viewmodels.DashboardDetailsViewModelFactory
import kotlinx.android.synthetic.main.fragment_dashboard_details.*

/**
 * A simple [Fragment] subclass.
 */
class DashboardDetailsFragment : Fragment() {

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
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_dashboard_details, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            DashboardDetailsViewModelFactory()
        ).get(DashboardDetailsVewModel::class.java)

        storyDetails = arguments!!.getParcelable<StoryDetails>("storyDetails")!!
        viewModel.setStoryDetails(storyDetails)
        newsTitleTv.text = storyDetails.title
        storyUrl.webViewClient = CustomWebViewClient(webViewProgressBar)
        storyUrl.loadUrl(storyDetails.url)
    }

}
