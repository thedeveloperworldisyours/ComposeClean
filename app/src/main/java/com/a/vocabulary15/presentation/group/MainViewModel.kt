package com.a.vocabulary15.presentation.group

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.domain.usecases.GetGroup
import com.a.vocabulary15.domain.usecases.GetGroups
import com.a.vocabulary15.domain.usecases.SetGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getGroup: GetGroup,
    private val setGroup: SetGroup,
    private val getGroups: GetGroups
) : ViewModel() {
    private val mutableGroup = MutableLiveData<GroupElementStates<List<Group>>>()
    val getGroupLiveData: LiveData<GroupElementStates<List<Group>>>
        get() = mutableGroup

    private val _state = mutableStateOf(GroupState())
    val state: State<GroupState> = _state
    private var getGroupsJob: Job? = null

    var isAddGroupOpen by mutableStateOf(false)

    init {
        getGroups()
    }

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
            is GroupElementStates.Error -> notifyErrorState(groupElementStates.error)
        }
    }

    private fun getGroups() {
        getGroupsJob?.cancel()
        getGroupsJob = getGroups.invoke().onEach { groups ->
            _state.value = state.value.copy(groups = groups)
        }.launchIn(viewModelScope)
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