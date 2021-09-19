package com.a.vocabulary15.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import com.a.vocabulary15.domain.model.GroupElementState
import com.a.vocabulary15.presentation.ui.composables.AddGroupButton
import com.a.vocabulary15.presentation.ui.composables.AddGroupTextField
import com.a.vocabulary15.presentation.ui.composables.GroupElementText
import com.a.vocabulary15.presentation.ui.composables.GroupListLazyColumn

@Composable
fun GroupScreen(liveData: LiveData<GroupElementState>, onClick: ()-> Unit):String {
    val groupElementStates: GroupElementState by liveData.observeAsState(initial = GroupElementState.Loading)
    var returnName = ""

    when(groupElementStates) {
        is GroupElementState.Loading -> {
            Column(modifier = Modifier.fillMaxHeight()) {
                returnName = AddGroupTextField()
                AddGroupButton(onClick = onClick)
            }
        }
        is GroupElementState.GroupElementData -> {
            Column(modifier = Modifier.fillMaxHeight()) {
                returnName = AddGroupTextField()
                AddGroupButton(onClick = onClick)
                GroupListLazyColumn((groupElementStates as GroupElementState.GroupElementData).data)
            }
        }
        else -> GroupElementText(text = "Nothing to show")
    }
    return returnName
}