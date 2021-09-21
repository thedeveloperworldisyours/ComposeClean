package com.a.vocabulary15.domain.model

sealed class GroupElementStates {
    object Loading : GroupElementStates()
    class Data(val data: List<Group>) : GroupElementStates()
    class Error(val error: Throwable) : GroupElementStates()
}