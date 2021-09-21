package com.a.vocabulary15.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.presentation.ui.composables.AddGroupButton
import com.a.vocabulary15.presentation.ui.composables.AddGroupTextField
import com.a.vocabulary15.presentation.ui.composables.GroupElementText
import com.a.vocabulary15.presentation.ui.composables.GroupListLazyColumn

@Composable
fun GroupScreen(liveData: LiveData<GroupElementStates>, onClick: ()-> Unit):String {
    val groupElementStates: GroupElementStates by liveData.observeAsState(initial = GroupElementStates.Loading)
    var returnName = ""

    when(groupElementStates) {
        is GroupElementStates.Loading -> {
            Column(modifier = Modifier.fillMaxHeight()) {
                returnName = AddGroupTextField()
                AddGroupButton(onClick = onClick)
            }
        }
        is GroupElementStates.Data -> {
            Column(modifier = Modifier.fillMaxHeight()) {
                returnName = AddGroupTextField()
                AddGroupButton(onClick = onClick)
                GroupListLazyColumn((groupElementStates as GroupElementStates.Data).data)
            }
        }
        else -> GroupElementText(text = "Nothing to show", modifier = Modifier.fillMaxSize())
    }
    return returnName
}