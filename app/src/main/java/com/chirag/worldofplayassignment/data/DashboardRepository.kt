package com.chirag.worldofplayassignment.data

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.chirag.worldofplayassignment.data.model.Resource
import com.chirag.worldofplayassignment.data.model.ResourceState
import com.chirag.worldofplayassignment.data.model.StoryDetails
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.collections.ArrayList


/**
 * Created by Chirag Sidhiwala on 24/4/20.
 */
class DashboardRepository(
    private val compositeDisposable: CompositeDisposable,
    private val assignmentServiceApi: AssignmentServiceApi,
    private val lifecycleOwner: LifecycleOwner
) {

    companion object {
        const val MAX_STORIES_CHUNK_SIZE = 20L
        private const val MAXIMUM_THREADS = 5
    }

    lateinit var executor: Executor

    /**
     * List of stories live data
     */
    lateinit var mStoryLiveData: LiveData<PagedList<StoryDetails>>

//    /**
//     * List of stories live data
//     */
//    private var mStoryMutableLiveData: MutableLiveData<PagedList<StoryDetails>>


    /**
     * Live data to notify if there is more stories to load
     */
    private val isMoreItemsAvailable: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Linked List of all the story ids received from the server. LinkedList because order of the ids need to be maintained.
     */
    private val listOfStoryId = LinkedList<Long>()

    /**
     * List of stories loaded
     */
    private val listOfStories = LinkedList<StoryDetails>()

    /**
     * To show more data
     */
    fun getMoreItemsLiveData(): LiveData<Boolean> {
        return isMoreItemsAvailable
    }

//    init {
//        mStoryMutableLiveData = MutableLiveData()
//    }

    /**
     * Live data to observe list of stories
     */
    fun getStoryListLiveData(): LiveData<PagedList<StoryDetails>> {
        return mStoryLiveData
    }


    fun getStoriesList() {
        executor = Executors.newFixedThreadPool(MAXIMUM_THREADS)
        val storiesDataSourceFactory = StoriesDataSourceFactory(this, lifecycleOwner, compositeDisposable, assignmentServiceApi)
        val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setInitialLoadSizeHint(MAX_STORIES_CHUNK_SIZE.toInt())
            .setPageSize(MAX_STORIES_CHUNK_SIZE.toInt()).build()
        mStoryLiveData = LivePagedListBuilder(storiesDataSourceFactory, pagedListConfig).setFetchExecutor(
            executor
        ).build()
//        mStoryMutableLiveData = mStoryLiveData as MutableLiveData<PagedList<StoryDetails>>
    }


    /**
     * Get all the IDS from the server then only process HackerViewModel.MAX_STORIES_CHUNK_SIZE so that only few stories are fetched first. Then more stories later.
     */
    fun getStories() {
        if (mStoryLiveData.value != null) {
            return
        }
        listOfStories.clear()
        //mStoryLiveData.postValue(Resource(ResourceState.LOADING, null))
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
        return take(MAX_STORIES_CHUNK_SIZE)
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
            //mStoryMutableLiveData.postValue(mStoryLiveData.value)
            //mStoryLiveData.polistOfStories.subList(0, listOfStories.size).toList())
            isMoreItemsAvailable.postValue(!listOfStoryId.isEmpty())
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
                .subscribe(getListOfStoriesObserver(), getThrowable())
        )
    }


    /* var topStoriesLiveData: MutableLiveData<List<String>>
     var topStories: List<String>? = null
         private set
     lateinit var executor: Executor
     private var lastFetchedStoryNumber: Int = 0

     */
    /**
     * Linked List of all the story ids received from the server. LinkedList because order of the ids need to be maintained.
     *//*
    private val listOfNewsId = LinkedList<Long>()
    */
    /**
     * List of stories live data
     *//*
    private val mStoryLiveData: MutableLiveData<Resource<List<StoryDetails>>> = MutableLiveData()
    */
    /**
     * List of stories loaded
     *//*
    private val listOfStories = LinkedList<StoryDetails>()

    companion object {
        private val MAXIMUM_THREADS = 5
        private val PAGE_SIZE = 20
    }

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        topStoriesLiveData = MutableLiveData()
        topStories = emptyList()
    }

    fun getTopStoriesIdList(): MutableLiveData<List<String>> {
        var resultOutput = emptyList<String>()
        dashboardDataSource.getStoryIdList() { result ->
            if (result is Result.Success) {
                setTopStories(result.data)
                resultOutput = result.data
                topStoriesLiveData.value = resultOutput
            }
        }
        return topStoriesLiveData
    }

    private fun setTopStories(stories: List<String>) {
        this.topStories = stories
    }

    fun getStoriesDetails(): LiveData<PagedList<StoryDetails>> {
        executor = Executors.newFixedThreadPool(MAXIMUM_THREADS)
        val storiesDataSourceFactory = StoriesDataSourceFactory(this)
        val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setInitialLoadSizeHint(PAGE_SIZE).setPageSize(PAGE_SIZE).build()
        return LivePagedListBuilder(storiesDataSourceFactory, pagedListConfig).setFetchExecutor(
            executor
        ).build()
    }

    fun getStoryDetailsList() {
        val storyDetailList: ArrayList<StoryDetails> =
            emptyArray<StoryDetails>().toList() as ArrayList<StoryDetails>
        var currentPosition = lastFetchedStoryNumber
        val finalIndex = currentPosition + 20
        //while (currentPosition < finalIndex) {
        dashboardDataSource.getStoryDetails(this.topStories!!.get(currentPosition)) {
            if (it is Result.Success) {
                listOfStories.add(it.data)
                topStories.
            }
        }
        //  }
        return storyDetailList
    }




    */
    /**
     * Get all the IDS from the server then only process HackerViewModel.MAX_STORIES_CHUNK_SIZE so that only few stories are fetched first. Then more stories later.
     *//*
    fun getStories() {
        if (mStoryLiveData.value != null && mStoryLiveData.value?.status == ResourceState.LOADING) {
            return
        }
        listOfStories.clear()
        mStoryLiveData.postValue(Resource(ResourceState.LOADING, null))
        compositeDisposable.add(
            dashboardDataSource.getStoryIdList {
                listOfNewsId.addAll(it)
            }
                .doOnSuccess { listOfNewsId.addAll(it) }
                .toObservable()
                .flatMap { Observable.fromIterable(it) }
                .fetchStoriesFromObservable()
                .toList()
                .subscribe(getListOfStoriesObserver(), getThrowable()))
    }
*/

}