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
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.presentation.ElementsActivity
import com.a.vocabulary15.presentation.element.DeleteAllDialog
import com.a.vocabulary15.presentation.element.DeleteElementDialog
import com.a.vocabulary15.presentation.ui.composables.AddGroupButton
import com.a.vocabulary15.presentation.ui.composables.AddGroupTextField
import com.a.vocabulary15.presentation.ui.composables.ExpandableElement
import com.a.vocabulary15.presentation.ui.composables.GroupElementText

@ExperimentalAnimationApi
@Composable
fun ElementScreen(
    activity: ElementsActivity
) {
    val groupElementStates: GroupElementStates<*> by activity.viewModel.genericLiveData.observeAsState(
        initial = GroupElementStates.Loading
    )
    when (groupElementStates) {
        is GroupElementStates.Loading -> {
            ElementLoadingScreen(
                activity
            )
        }
        is GroupElementStates.Data -> {
            ElementDataScreen(activity, groupElementStates)
        }
        else -> {
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ElementDataScreen(
    activity: ElementsActivity,
    groupElementStates: GroupElementStates<*>
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
                    IconButton(onClick = {
                        activity.viewModel.isDeleteAllOpen.value = true
                        /*activity.viewModel.deleteGroupWithElements(activity.idGroup.toInt())
                        activity.finish()*/
                    }) {
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
        DeleteAllDialog(activity)
        Box(
            Modifier
                .fillMaxSize()
        ) {
            ExpandableElement(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray),
                (groupElementStates as GroupElementStates.Data<*>).data as List<Element>,
                activity
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
                        activity.viewModel.setElement(
                            Element(
                                id = 0,
                                groupId = activity.idGroup.toInt(),
                                value = returnName,
                                image = "",
                                link = ""
                            )
                        )
                    })
                }
            }
        }
        DeleteElementDialog(activity)
    }
}

@ExperimentalAnimationApi
@Composable
fun ElementLoadingScreen(
    activity: ElementsActivity
) {

    var visible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Groups of Elements")
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
                text = activity.idGroup, modifier = Modifier
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
                        activity.viewModel.isDeleteElementOpen.value = true
                        /*visible = false
                        elementsViewModel.setElement(
                            Element(
                                id = 0,
                                groupId = idGroup.toInt(),
                                value = returnName,
                                image = "",
                                link = ""
                            )
                        )*/
                    })
                }
            }
        }
    }
}