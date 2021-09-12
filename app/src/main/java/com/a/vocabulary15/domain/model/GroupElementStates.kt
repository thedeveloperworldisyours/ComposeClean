package com.a.vocabulary15.domain.model

sealed class GroupElementStates {
    object Loading : GroupElementStates()
    class InsertGroupData(val groupLong: Long) : GroupElementStates()
    class Error(val error: Throwable) : GroupElementStates()
}