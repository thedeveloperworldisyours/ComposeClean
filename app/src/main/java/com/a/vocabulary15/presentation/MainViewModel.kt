package com.a.vocabulary15.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.domain.usecases.SetGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val setGroup: SetGroup
) : ViewModel() {
    private val mutableGroup = MutableLiveData<GroupElementStates>()
    val setGroupLiveData: LiveData<GroupElementStates>
        get() = mutableGroup

    fun insertGroup(group: Group) = viewModelScope.launch(Dispatchers.IO) {
        when (val groupElementStates = setGroup(group)) {
            is GroupElementStates.Loading -> notifyLoadingState()
            is GroupElementStates.InsertGroupData -> notifyGroupState(groupElementStates.groupLong)
            is GroupElementStates.Error -> notifyErrorState(groupElementStates.error)
        }
    }

    private fun notifyLoadingState() {
        mutableGroup.postValue(GroupElementStates.Loading)
    }

    private fun notifyGroupState(valueLong: Long) {
        mutableGroup.postValue(GroupElementStates.InsertGroupData(valueLong))
    }

    private fun notifyErrorState(error: Throwable) {
        mutableGroup.postValue(GroupElementStates.Error(error))
    }
}