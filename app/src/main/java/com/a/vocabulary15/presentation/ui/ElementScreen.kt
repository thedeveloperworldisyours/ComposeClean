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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.presentation.ElementsViewModel
import com.a.vocabulary15.presentation.ui.composables.*

@ExperimentalAnimationApi
@Composable
fun ElementScreen(
    liveData: LiveData<GroupElementStates<*>>,
    idGroup: String,
    elementsViewModel: ElementsViewModel,
    deleteClick: () -> Unit
) {
    val groupElementStates: GroupElementStates<*> by liveData.observeAsState(initial = GroupElementStates.Loading)
    when (groupElementStates) {
        is GroupElementStates.Loading -> {
            ElementLoadingScreen(
                idGroup = idGroup,
                elementsViewModel = elementsViewModel,
                deleteClick
            )
        }
        is GroupElementStates.Data -> {
            ElementDataScreen(idGroup, groupElementStates, elementsViewModel, deleteClick)
        }
        else -> {
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ElementDataScreen(
    idGroup: String,
    groupElementStates: GroupElementStates<*>,
    elementsViewModel: ElementsViewModel,
    deleteClick: () -> Unit
) {

    var visible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Groups of Elements")
                }, actions = {
                    IconButton(onClick = deleteClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete_forever),
                            contentDescription = "delete"
                        )
                    }
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
            ExpandableElement(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray),
                (groupElementStates as GroupElementStates.Data<*>).data as List<Element>,
                elementsViewModel
            )

            AnimatedVisibility(visible) {
                //Add Element
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
                        elementsViewModel.setElement(
                            Element(
                                id = 0,
                                groupId = idGroup.toInt(),
                                value = returnName,
                                image = "",
                                link = ""
                            )
                        )
                    })
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ElementLoadingScreen(
    idGroup: String,
    elementsViewModel: ElementsViewModel,
    deleteClick: () -> Unit
) {

    var visible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Groups of Elements")
                }, actions = {
                    IconButton(onClick = deleteClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete_forever),
                            contentDescription = "delete"
                        )
                    }
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
            GroupElementText(
                text = idGroup, modifier = Modifier
                    .align(
                        Alignment.Center
                    )
            )

            AnimatedVisibility(visible) {
                //Add Element
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
                        elementsViewModel.setElement(
                            Element(
                                id = 0,
                                groupId = idGroup.toInt(),
                                value = returnName,
                                image = "",
                                link = ""
                            )
                        )
                    })
                }
            }
        }
    }
}