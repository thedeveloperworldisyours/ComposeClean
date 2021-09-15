package com.a.vocabulary15.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.presentation.ui.composables.AddGroupButton
import com.a.vocabulary15.presentation.ui.composables.AddGroupTextField
import com.a.vocabulary15.presentation.ui.composables.GroupElementText

@Composable
fun GroupScreen(liveData: LiveData<GroupElementStates<Long>>, nameGroup: String, onClick: ()-> Unit):String {
    val groupElementStates: GroupElementStates<Long> by liveData.observeAsState(initial = GroupElementStates.Loading)
    var returnName = ""

    when(groupElementStates) {
        is GroupElementStates.Loading -> {
            Column(modifier = Modifier.fillMaxHeight()) {
                returnName = AddGroupTextField()
                AddGroupButton(onClick = onClick)
            }
        }
        is GroupElementStates.GroupElementData -> {
            GroupElementText(text = "$nameGroup has been added")
        }
        else -> GroupElementText(text = "Nothing to show")
    }
    return returnName
}