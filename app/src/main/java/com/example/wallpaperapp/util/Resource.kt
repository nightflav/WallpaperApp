package com.example.wallpaperapp.util

sealed class Resource<T>(val value: T?) {
    data class Error<T>(val error: Throwable, val prevState: T?) : Resource<T>(prevState)
    data class Success<T>(val newState: T) : Resource<T>(newState)
    data class Loading<T>(val prevState: T?) : Resource<T>(prevState)
}