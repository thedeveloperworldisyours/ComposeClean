package com.a.vocabulary15.domain.model

sealed class GroupElementStates<out T> {
    object Loading : GroupElementStates<Nothing>()
    class Data<out T>(val data:  T) : GroupElementStates<T>()
    class Error(val error: Throwable) : GroupElementStates<Nothing>()
}