package com.chirag.worldofplayassignment.data

import androidx.lifecycle.LifecycleOwner
import androidx.paging.DataSource
import com.chirag.worldofplayassignment.data.model.StoryDetails
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Chirag Sidhiwala on 25/4/20.
 */
class StoriesDataSourceFactory(dashboardRepository: DashboardRepository, lifecycleOwnerObj: LifecycleOwner, val compositeDisposable: CompositeDisposable,
                               val assignmentServiceApi: AssignmentServiceApi) :
    DataSource.Factory<Int, StoryDetails>() {
    private val dashboardRepo = dashboardRepository
    private lateinit var dataSource: StoriesDataSource
    private val lifecycleOwner = lifecycleOwnerObj
    override fun create(): DataSource<Int, StoryDetails> {
        dataSource = StoriesDataSource(dashboardRepo, lifecycleOwner, compositeDisposable, assignmentServiceApi)
        return dataSource
    }
}