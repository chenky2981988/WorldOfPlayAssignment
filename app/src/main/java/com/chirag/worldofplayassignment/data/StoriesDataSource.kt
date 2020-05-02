package com.chirag.worldofplayassignment.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.chirag.worldofplayassignment.data.model.NetworkState
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
    private val compositeDisposable: CompositeDisposable,
    private val assignmentServiceApi: AssignmentServiceApi
) :
    PageKeyedDataSource<Int, StoryDetails>() {

    /**
     * List of stories loaded
     */
    private val listOfStories = LinkedList<StoryDetails>()

    /**
     * Linked List of all the story ids received from the server. LinkedList because order of the ids need to be maintained.
     */
    private val listOfStoryId = LinkedList<Long>()

    val networkState = MutableLiveData<NetworkState>()

    //val initialLoad = MutableLiveData<NetworkState>()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, StoryDetails>
    ) {
        networkState.postValue(NetworkState.LOADING)
        //initialLoad.postValue(NetworkState.LOADING)
        getStories(callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, StoryDetails>) {
        networkState.postValue(NetworkState.MORE_LOADING)
        getNext20Stories(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, StoryDetails>) {
        TODO("Not yet implemented")
    }

    private suspend fun observeLiveData(
        loadInitialCallback: LoadInitialCallback<Int, StoryDetails>,
        listData: LinkedList<StoryDetails>
    ) {
        withContext(Dispatchers.Main) {
            networkState.postValue(NetworkState.SUCCESS)
            loadInitialCallback.onResult(listData, null, 1)
        }
    }

    private suspend fun observeMoreLiveData(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, StoryDetails>,
        listData: LinkedList<StoryDetails>
    ) {
        withContext(Dispatchers.Main) {
            networkState.postValue(NetworkState.SUCCESS)
            callback.onResult(listData, params.key + 1)
        }
    }


    //API call

    /**
     * Get all the IDS from the server then only process HackerViewModel.MAX_STORIES_CHUNK_SIZE so that only few stories are fetched first. Then more stories later.
     */
    private fun getStories(loadInitialCallback: LoadInitialCallback<Int, StoryDetails>) {
        listOfStories.clear()
        compositeDisposable.add(
            assignmentServiceApi.getTopStories()
                .doOnSuccess { listOfStoryId.addAll(it) }
                .toObservable()
                .flatMap { Observable.fromIterable(it) }
                .fetchStoriesFromObservable()
                .toList()
                .subscribe(getListOfStoriesObserver(loadInitialCallback), getThrowable()))
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
    private fun getListOfStoriesObserver(loadInitialCallback: LoadInitialCallback<Int, StoryDetails>): ((t: List<StoryDetails>) -> Unit)? {
        return {
            listOfStories.addAll(it)
            CoroutineScope(Dispatchers.IO).launch {
                observeLiveData(loadInitialCallback, listOfStories)
            }
        }
    }

    /**
     * Observer for more list of stories
     */
    private fun getMoreListOfStoriesObserver(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, StoryDetails>
    ): ((t: List<StoryDetails>) -> Unit)? {
        return {
            listOfStories.addAll(it)
            CoroutineScope(Dispatchers.IO).launch {
                observeMoreLiveData(params, callback, listOfStories)
            }
        }
    }

    private fun getThrowable(): ((t: Throwable) -> Unit)? {
        return {
            Log.e("TAG", "Error : ${it.message}")
            val error = NetworkState.error(it.message)
            networkState.postValue(error)
        }
    }

    /**
     * Get the id from the listOfStoryId and fetch stories from server
     */
    private fun getNext20Stories(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, StoryDetails>
    ) {
        val subList = LinkedList<Long>()
        subList.addAll(listOfStoryId.subList(0, listOfStoryId.size))
        compositeDisposable.add(
            Observable.fromIterable(subList)
                .fetchStoriesFromObservable()
                .toList()
                .subscribe(getMoreListOfStoriesObserver(params, callback), getThrowable())
        )
    }


}