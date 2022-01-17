package com.a.vocabulary15.presentation.group

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getGroups: GetGroups,
    private val setGroup: SetGroup
) : ViewModel() {

    val groups = getGroups.invoke()

    var isAddGroupOpen by mutableStateOf(false)

    fun insertAndGetGroup(group: Group) = viewModelScope.launch(Dispatchers.IO) {
        setGroup(group)
    }

}