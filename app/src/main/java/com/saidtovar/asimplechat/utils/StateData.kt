package com.saidtovar.asimplechat.utils

sealed interface StateData<T> {
    data class Success<T>(val data: T) : StateData<T>
    data class Error<T>(val error: String) : StateData<T>
    class Loading<T> : StateData<T>
}