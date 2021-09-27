package com.a.vocabulary15.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.presentation.MainViewModel
import com.a.vocabulary15.presentation.ui.composables.AddGroupButton
import com.a.vocabulary15.presentation.ui.composables.AddGroupTextField
import com.a.vocabulary15.presentation.ui.composables.GroupElementText
import com.a.vocabulary15.presentation.ui.composables.GroupListLazyColumn

@ExperimentalAnimationApi
@Composable
fun GetGroupScreen(
    liveData: LiveData<GroupElementStates<List<Group>>>,
    mainViewModel: MainViewModel,
    itemClickable: (Int) -> Unit
) {
    val groupElementStates: GroupElementStates<List<Group>> by liveData.observeAsState(initial = GroupElementStates.Loading)
    when (groupElementStates) {
        is GroupElementStates.Loading -> {
            Box(Modifier.fillMaxSize()) {
                GroupElementText(text = "Loading...", Modifier.align(Alignment.Center))
                FloatingActionButton(
                    onClick = { }, modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                ) {

                }
            }
        }
        is GroupElementStates.Data -> {
            var visible by remember { mutableStateOf(false) }
            Box(Modifier.fillMaxSize()) {
                GroupListLazyColumn(
                    (groupElementStates as GroupElementStates.Data).data,
                    itemClickable
                )
                FloatingActionButton(
                    onClick = { visible = !visible }, modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                ) {}
                if (visible) {
                    //Add Group
                    AnimatedVisibility(
                        visible = visible,
                        enter = expandHorizontally(),
                        exit = shrinkHorizontally()
                    ) {
                        Box(
                            modifier = Modifier
                                .height(178.dp)
                                .fillMaxWidth()
                                .background(Color.White)
                                .align(
                                    Alignment.TopCenter
                                )
                        )
                        Column(modifier = Modifier.fillMaxHeight()) {
                            val returnName = AddGroupTextField()
                            AddGroupButton(onClick = {
                                visible = false
                                mainViewModel.insertAndGetGroup(Group(0, returnName))
                            })
                        }
                    }
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
                    onClick = {}, modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                ) {

                }
            }
        }
    }
}