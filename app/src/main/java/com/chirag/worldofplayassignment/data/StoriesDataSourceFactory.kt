package com.chirag.worldofplayassignment.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.chirag.worldofplayassignment.data.model.StoryDetails
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Chirag Sidhiwala on 25/4/20.
 */
class StoriesDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val assignmentServiceApi: AssignmentServiceApi
) :
    DataSource.Factory<Int, StoryDetails>() {
    val storiesDataSourceLiveData = MutableLiveData<StoriesDataSource>()

    override fun create(): DataSource<Int, StoryDetails> {
       val storiesDataSource = StoriesDataSource(compositeDisposable, assignmentServiceApi)
        storiesDataSourceLiveData.postValue(storiesDataSource)
        return storiesDataSource
    }
}