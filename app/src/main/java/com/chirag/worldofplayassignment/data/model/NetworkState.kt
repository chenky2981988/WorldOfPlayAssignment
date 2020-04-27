package com.chirag.worldofplayassignment.data.model


/**
 * Created by Chirag Sidhiwala on 27/4/20.
 */


data class NetworkState constructor(
    val status: ResourceState,
    val message: String? = null
) {
    companion object {
        val SUCCESS = NetworkState(
            ResourceState.SUCCESS
        )
        val LOADING = NetworkState(
            ResourceState.LOADING
        )
        val MORE_LOADING =
            NetworkState(
                ResourceState.MORE_LOADING
            )
        fun error(msg: String?) =
            NetworkState(
                ResourceState.ERROR,
                msg
            )
    }
}