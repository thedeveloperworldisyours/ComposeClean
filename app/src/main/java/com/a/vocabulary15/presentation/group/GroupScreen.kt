package com.a.vocabulary15.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.presentation.MainViewModel
import com.a.vocabulary15.presentation.ui.composables.AddGroupButton
import com.a.vocabulary15.presentation.ui.composables.AddGroupTextField
import com.a.vocabulary15.presentation.ui.composables.GroupElementText
import com.a.vocabulary15.presentation.ui.composables.GroupListLazyColumn

@ExperimentalAnimationApi
@Composable
fun GroupScreen(
    liveData: LiveData<GroupElementStates<List<Group>>>,
    mainViewModel: MainViewModel,
    itemClickable: (Int, String) -> Unit
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
            DataGroupScreen(groupElementStates, mainViewModel, itemClickable)
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

@ExperimentalAnimationApi
@Composable
fun DataGroupScreen(
    groupElementStates: GroupElementStates<List<Group>>,
    mainViewModel: MainViewModel,
    itemClickable: (Int, String) -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontWeight = FontWeight.Bold, text = stringResource(id = R.string.app_name))
                }
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { visible = !visible }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = ""
                )
            }
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
        ) {

            GroupListLazyColumn(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                (groupElementStates as GroupElementStates.Data).data,
                itemClickable
            )
            AnimatedVisibility (visible) {
                //Add Group
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
                    val returnName = AddGroupTextField(stringResource(id = R.string.enter_name))
                    AddGroupButton(onClick = {
                        visible = false
                        mainViewModel.insertAndGetGroup(Group(0, returnName))
                    })
                }
            }
        }
    }
}
