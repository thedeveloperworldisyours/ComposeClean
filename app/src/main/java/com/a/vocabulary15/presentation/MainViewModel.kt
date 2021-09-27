package com.a.vocabulary15.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.domain.usecases.GetGroup
import com.a.vocabulary15.domain.usecases.SetGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val getGroup: GetGroup,
    private val setGroup: SetGroup
) : ViewModel() {
    private val mutableGroup = MutableLiveData<GroupElementStates<List<Group>>>()
    val getGroupLiveData: LiveData<GroupElementStates<List<Group>>>
        get() = mutableGroup

    fun getGroup() = viewModelScope.launch(Dispatchers.IO) {
        when (val groupElementStates: GroupElementStates<List<Group>> = getGroup.invoke()) {
            is GroupElementStates.Loading -> notifyLoadingStates()
            is GroupElementStates.Data<List<Group>> -> notifyGroupState(groupElementStates.data)
            is GroupElementStates.Error -> notifyErrorState(groupElementStates.error)
        }
    }

    fun insertAndGetGroup(group: Group) = viewModelScope.launch(Dispatchers.IO) {
        when (val groupElementStates = setGroup(group)) {
            is GroupElementStates.Loading -> notifyLoadingStates()
            is GroupElementStates.Data<List<Group>> -> notifyGroupState(groupElementStates.data)
            is Error -> {
                val error= Throwable()
                notifyErrorState(error)
            }
        }
    }

    private fun notifyLoadingStates() {
        mutableGroup.postValue(GroupElementStates.Loading)

    }

    private fun notifyGroupState(groupList: List<Group>) {
        mutableGroup.postValue(GroupElementStates.Data(groupList))
    }

    private fun notifyErrorState(error: Throwable) {
        mutableGroup.postValue(GroupElementStates.Error(error))
    }
}