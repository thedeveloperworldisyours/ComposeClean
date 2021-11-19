package com.a.vocabulary15.presentation.element

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.presentation.ElementsActivity
import com.a.vocabulary15.presentation.ui.composables.AddGroupTextField
import com.a.vocabulary15.presentation.ui.composables.ExpandableCard
import com.a.vocabulary15.presentation.ui.composables.GroupElementText
import com.a.vocabulary15.presentation.ui.theme.Typography

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
            val expandedItem = activity.viewModel.expandedList.collectAsState()
            LazyColumn(modifier = Modifier
                .fillMaxWidth()) {
                itemsIndexed(items = (groupElementStates as GroupElementStates.Data<*>).data as List<Element>) { _, item: Element ->
                    ExpandableCard(
                        element = item,
                        onCardArrowClick = { activity.viewModel.cardArrowClick(item.id) },
                        expanded = expandedItem.value.contains(item.id),
                        activity
                    )
                }
            }
            AnimatedVisibility(visible) {
                //Add Element
                Box(
                    modifier = Modifier
                        .height(458.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                        .align(
                            Alignment.TopCenter
                        )
                )
                Column(modifier = Modifier.fillMaxHeight()) {
                    val linkGoogle = stringResource(id =R.string.google_reference)
                    val returnName = AddGroupTextField(stringResource(id = R.string.enter_name))
                    val returnLink = AddGroupTextField(stringResource(id = R.string.enter_link))
                    Button(onClick= {
                        startActivity(activity, Intent(Intent.ACTION_VIEW, Uri.parse(linkGoogle)), null)
                    },
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.search_word_link),
                            color = MaterialTheme.colors.secondary,
                            style = Typography.button
                        )
                    }
                    val returnImageLink = AddGroupTextField(stringResource(id = R.string.enter_image_link))
                    val link = stringResource(id =R.string.word_reference)
                    Button(onClick= {
                        startActivity(activity, Intent(Intent.ACTION_VIEW, Uri.parse(link)), null)
                    },
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.search_image),
                            color = MaterialTheme.colors.secondary,
                            style = Typography.button
                        )
                    }
                    Button(
                        onClick = {
                            visible = false
                            activity.viewModel.setElement(
                                Element(
                                    id = 0,
                                    groupId = activity.idGroup.toInt(),
                                    value = returnName,
                                    image = returnImageLink,
                                    link = returnLink
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_element),
                            color = MaterialTheme.colors.secondary,
                            style = Typography.button
                        )
                    }
                }
            }
        }
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
        }
    }
}