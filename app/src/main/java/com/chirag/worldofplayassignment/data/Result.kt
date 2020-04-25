package com.chirag.worldofplayassignment.data

import com.android.volley.VolleyError

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class Volleyerror(val volleyError: VolleyError) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Volleyerror -> "VolleyError[error=$volleyError]"
        }
    }
}
