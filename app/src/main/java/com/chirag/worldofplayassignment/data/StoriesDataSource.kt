package com.chirag.worldofplayassignment.data

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.chirag.worldofplayassignment.data.model.StoryDetails
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


/**
 * Created by Chirag Sidhiwala on 25/4/20.
 */
class StoriesDataSource(
    val dashboardRepository: DashboardRepository,
    private val lifecycleOwner: LifecycleOwner, val compositeDisposable: CompositeDisposable,
    val assignmentServiceApi: AssignmentServiceApi
) :
    PageKeyedDataSource<Int, StoryDetails>() {
    /**
     * List of stories live data
     */
    lateinit var mStoryLiveData: PagedList<StoryDetails>
    /**
     * List of stories loaded
     */
    private val listOfStories = LinkedList<StoryDetails>()
    /**
     * Linked List of all the story ids received from the server. LinkedList because order of the ids need to be maintained.
     */
    private val listOfStoryId = LinkedList<Long>()
    lateinit var loadInitialCallback: LoadInitialCallback<Int, StoryDetails>
    lateinit var loadAftercallback: LoadCallback<Int, StoryDetails>
    lateinit var paramsData: LoadParams<Int>

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, StoryDetails>
    ) {
        loadInitialCallback = callback
        getStories()
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, StoryDetails>) {
       // dashboardRepository.getNext20Stories()
        loadAftercallback = callback
        paramsData = params
        getNext20Stories()

//        withContext(Dispatchers.Main) {
//            dashboardRepository.getStoryListLiveData().observe(lifecycleOwner, Observer {
//                callback.onResult(it, params.key + 1)
//            })
//        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, StoryDetails>) {
        TODO("Not yet implemented")
    }

    private suspend fun observeLiveData(listData: LinkedList<StoryDetails>) {
        withContext(Dispatchers.Main) {
            loadInitialCallback.onResult(listData, null, 1)
//            dashboardRepository.getStoryListLiveData().observe(lifecycleOwner, Observer {
//
//            })
        }
    }

    private suspend fun observeMoreLiveData(params: LoadParams<Int>, callback: LoadCallback<Int, StoryDetails>) {
        withContext(Dispatchers.Main) {
            dashboardRepository.getStoryListLiveData().observe(lifecycleOwner, Observer {
                callback.onResult(it, params.key + 1)
            })
        }
    }



    //API call

    /**
     * Get all the IDS from the server then only process HackerViewModel.MAX_STORIES_CHUNK_SIZE so that only few stories are fetched first. Then more stories later.
     */
    fun getStories() {
        listOfStories.clear()
        compositeDisposable.add(
            assignmentServiceApi.getTopStories()
                .doOnSuccess { listOfStoryId.addAll(it) }
                .toObservable()
                .flatMap { Observable.fromIterable(it) }
                .fetchStoriesFromObservable()
                .toList()
                .subscribe(getListOfStoriesObserver(), getThrowable()))
    }

    /**
     * Extension function to fetch the stories from server with max chunk size
     */
    private fun <T> Observable<T>.fetchStoriesFromObservable(): Observable<StoryDetails> {
        return take(DashboardRepository.MAX_STORIES_CHUNK_SIZE)
            .getStoryFromServer(assignmentServiceApi)
            .doOnNext {
                listOfStoryId.remove(it.id.toLong())
                Log.d("TAG", "Story Fetched : ${it.title}")
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Load story from server
     */
    private fun <T> Observable<T>.getStoryFromServer(assignmentServiceApi: AssignmentServiceApi): Observable<StoryDetails> {
        return concatMap { assignmentServiceApi.getStoryDetails(it.toString()).toObservable() }
    }

    /**
     * Observer for list of stories
     */
    private fun getListOfStoriesObserver(): ((t: List<StoryDetails>) -> Unit)? {
        return {
            listOfStories.addAll(it)
            CoroutineScope(Dispatchers.IO).launch {
                observeLiveData(listOfStories)
            }
        }
    }

    /**
     * Observer for more list of stories
     */
    private fun getMoreListOfStoriesObserver(): ((t: List<StoryDetails>) -> Unit)? {
        return {
            listOfStories.addAll(it)
            CoroutineScope(Dispatchers.IO).launch {
                observeMoreLiveData(paramsData, loadAftercallback)
            }
        }
    }

    private fun getThrowable(): ((t: Throwable) -> Unit)? {
        return {
            Log.e("TAG", "Error : ${it.message}")
            //mStoryLiveData.postValue(Resource(ResourceState.ERROR, null))
        }
    }

    /**
     * Get the id from the listOfStoryId and fetch stories from server
     */
    fun getNext20Stories() {
        // mStoryLiveData.postValue(Resource(ResourceState.MORE_LOADING, null))
        val subList = LinkedList<Long>()
        subList.addAll(listOfStoryId.subList(0, listOfStoryId.size))
        compositeDisposable.add(
            Observable.fromIterable(subList)
                .fetchStoriesFromObservable()
                .toList()
                .subscribe(getMoreListOfStoriesObserver(), getThrowable())
        )
    }


}