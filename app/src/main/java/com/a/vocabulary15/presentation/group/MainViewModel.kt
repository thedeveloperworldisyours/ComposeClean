package com.a.vocabulary15.presentation.group

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Group
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
    private val getGroups: GetGroups,
    private val setGroup: SetGroup
) : ViewModel() {

    private val _state = mutableStateOf(GroupState())
    val state: State<GroupState> = _state
    private var getGroupsJob: Job? = null

    var isAddGroupOpen by mutableStateOf(false)

    init {
        getGroups()
    }

    fun insertAndGetGroup(group: Group) = viewModelScope.launch(Dispatchers.IO) {
        setGroup(group)
    }

    private fun getGroups() {
        getGroupsJob?.cancel()
        getGroupsJob = getGroups.invoke().onEach { groups ->
            _state.value = state.value.copy(groups = groups)
        }.launchIn(viewModelScope)
    }

}