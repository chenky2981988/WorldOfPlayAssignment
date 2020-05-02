package com.chirag.worldofplayassignment.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.chirag.worldofplayassignment.data.model.NetworkState
import com.chirag.worldofplayassignment.data.model.StoryDetails
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Created by Chirag Sidhiwala on 24/4/20.
 */
class DashboardRepository(
    compositeDisposable: CompositeDisposable,
    assignmentServiceApi: AssignmentServiceApi
) {

    companion object {
        const val MAX_STORIES_CHUNK_SIZE = 20L
        private const val MAXIMUM_THREADS = 5
    }

    /**
     * List of stories live data
     */
    lateinit var mStoryLiveData: LiveData<PagedList<StoryDetails>>

    private val executor: Executor
    private val storiesDataSourceFactory: StoriesDataSourceFactory
    private val pagedListConfig: PagedList.Config

    init {
        executor = Executors.newFixedThreadPool(MAXIMUM_THREADS)
        storiesDataSourceFactory =
            StoriesDataSourceFactory(compositeDisposable, assignmentServiceApi)
        pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setInitialLoadSizeHint(MAX_STORIES_CHUNK_SIZE.toInt())
            .setPageSize(MAX_STORIES_CHUNK_SIZE.toInt()).build()
    }


    /**
     * Live data to observe list of stories
     */
    fun getStoryListLiveData(): LiveData<PagedList<StoryDetails>> {
        return mStoryLiveData
    }


    fun getStoriesList() {
        mStoryLiveData =
            LivePagedListBuilder(storiesDataSourceFactory, pagedListConfig).setFetchExecutor(
                executor
            ).build()
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<StoriesDataSource, NetworkState>(
        storiesDataSourceFactory.storiesDataSourceLiveData, { it.networkState })

    fun refreshStories() {
        storiesDataSourceFactory.storiesDataSourceLiveData.value!!.invalidate()
    }
/*
    */
    /**
     * Get all the IDS from the server then only process HackerViewModel.MAX_STORIES_CHUNK_SIZE so that only few stories are fetched first. Then more stories later.
     *//*
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

    */
    /**
     * Extension function to fetch the stories from server with max chunk size
     *//*
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

    */
    /**
     * Load story from server
     *//*
    private fun <T> Observable<T>.getStoryFromServer(assignmentServiceApi: AssignmentServiceApi): Observable<StoryDetails> {
        return concatMap { assignmentServiceApi.getStoryDetails(it.toString()).toObservable() }
    }

    */
    /**
     * Observer for list of stories
     *//*
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

    */
    /**
     * Get the id from the listOfStoryId and fetch stories from server
     *//*
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
    }*/

}