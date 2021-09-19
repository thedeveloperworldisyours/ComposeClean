package com.a.vocabulary15.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementState
import com.a.vocabulary15.domain.model.GroupElementState.*
import com.a.vocabulary15.domain.usecases.SetGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val setGroup: SetGroup
) : ViewModel() {
    private val mutableGroup = MutableLiveData<GroupElementState>()
    val setGroupLiveData: LiveData<GroupElementState>
        get() = mutableGroup

    fun insertGroup(group: Group) = viewModelScope.launch(Dispatchers.IO) {
        when (val groupElementStates = setGroup(group)) {
            is Loading -> notifyLoadingState()
            is GroupElementData -> notifyGroupState(groupElementStates.data)
            is Error -> notifyErrorState(groupElementStates.error)
        }
    }

    private fun notifyLoadingState() {
        mutableGroup.postValue(Loading)
    }

    private fun notifyGroupState(groupList: List<Group>) {
        mutableGroup.postValue(GroupElementData(groupList))
    }

    private fun notifyErrorState(error: Throwable) {
        mutableGroup.postValue(Error(error))
    }
}