package com.chirag.worldofplayassignment.data.model


/**
 * Created by Chirag Sidhiwala on 26/4/20.
 */
open class Resource<out T> constructor(val status: ResourceState, val data: T?)