package com.a.vocabulary15.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.presentation.ui.composables.AddGroupButton
import com.a.vocabulary15.presentation.ui.composables.GroupElementText

@Composable
fun GroupScreen(liveData: LiveData<GroupElementStates>, onClick: ()-> Unit) {
    val groupElementStates: GroupElementStates by liveData.observeAsState(initial = GroupElementStates.Loading)

    when(groupElementStates) {
        is GroupElementStates.Loading -> AddGroupButton(onClick = onClick)
        is GroupElementStates.InsertGroupData -> {
            GroupElementText(text = "Group Add")
        }
        else -> GroupElementText(text = "Nothing to show")
    }
}