package com.a.vocabulary15.domain.model

sealed class GroupElementStates<out T> {
    object Loading : GroupElementStates<Nothing>()
    class GroupElementData<out T>(val value: T) : GroupElementStates<T>()
    class Error(val error: Throwable) : GroupElementStates<Nothing>()
}