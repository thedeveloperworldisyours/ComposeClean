package com.a.vocabulary15.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.presentation.ui.composables.GroupElementText
import com.a.vocabulary15.presentation.ui.composables.GroupListLazyColumn

@Composable
fun GetGroupScreen(liveData: LiveData<GroupElementStates<List<Group>>>, onClick: () -> Unit, itemClickable: (Int)->Unit) {
    val groupElementStates: GroupElementStates<List<Group>> by liveData.observeAsState(initial = GroupElementStates.Loading)
    when (groupElementStates) {
        is GroupElementStates.Loading -> {
            Box(Modifier.fillMaxSize()) {
                GroupElementText(text = "Loading...", Modifier.align(Alignment.Center))
                FloatingActionButton(
                    onClick = onClick, modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                ) {

                }
            }
        }
        is GroupElementStates.Data -> {
            Box(Modifier.fillMaxSize()) {
                GroupListLazyColumn((groupElementStates as GroupElementStates.Data).data, itemClickable)
                FloatingActionButton(
                    onClick = onClick, modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                ) {

                }
            }
        }
        else -> {
            Box(Modifier.fillMaxSize()) {
                GroupElementText(
                    text = "Nothing to show",
                    modifier = Modifier.align(Alignment.Center)
                )
                FloatingActionButton(
                    onClick = onClick, modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                ) {

                }
            }
        }
    }

}