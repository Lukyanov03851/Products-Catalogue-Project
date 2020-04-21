package ua.lukyanov.catalogue.util

import android.util.Log


data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        private const val TAG = "Resource"

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String): Resource<T> {
            Log.e(TAG, "Error: $msg")
            return Resource(Status.ERROR, null, msg)
        }
    }
}