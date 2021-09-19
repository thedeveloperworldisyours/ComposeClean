package com.a.vocabulary15.domain.model

sealed class GroupElementState {
    object Loading : GroupElementState()
    class GroupElementData(val data: List<Group>) : GroupElementState()
    class Error(val error: Throwable) : GroupElementState()
}