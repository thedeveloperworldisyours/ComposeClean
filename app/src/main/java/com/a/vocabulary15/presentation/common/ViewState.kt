package com.a.vocabulary15.presentation.common

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    data class Success<out T>(val value: T?) : ViewState<T>()
}
