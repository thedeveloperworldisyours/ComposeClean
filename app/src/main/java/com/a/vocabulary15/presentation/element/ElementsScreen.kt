package com.a.vocabulary15.presentation.element

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element

@Composable
fun ElementScreen(
    activity: ElementsActivity,
    onClickTest: () -> Unit
) {
    val state = activity.viewModel.state.value
    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = activity.elementName
                    )
                }, actions = {
                    IconButton(onClick = {
                        onClickTest()
                    }) {Icon(
                        painter = painterResource(id = R.drawable.ic_check_knowledge),
                        contentDescription = stringResource(id = R.string.check_your_knowledge)
                    )}
                    IconButton(onClick = {
                        activity.viewModel.isDeleteAllOpen = true
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete_forever),
                            contentDescription = stringResource(id = R.string.delete)
                        )
                    }
                }
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    activity.viewModel.isAddElementOpen = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = ""
                )
            }
        }
    ) {
        DetailDialog(activity)
        DeleteAllDialog(activity)
        AddElementDialog(activity = activity)
        EditElementDialog(activity)
        DeleteElementDialog(activity)
        Box(
            Modifier
                .fillMaxSize()
        ) {
            val listItems = state.elements
            /*if (listItems.isEmpty()) {
                activity.viewModel.isAddElementOpen = true
                GroupElementText(
                    text = stringResource(id = R.string.empty_group, activity.elementName),
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                )
            } else {*/
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    itemsIndexed(items = listItems) { _, item: Element ->
                        ElementListItem(item, clickableItem = {
                            activity.viewModel.item = item
                            activity.viewModel.isDetailElementOpen = true
                        })
                    }
                }
            //}
        }
    }
}

/*
@Composable
fun ElementLoadingScreen(
    activity: ElementsActivity
) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.loading))
                }
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                }
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
}*/
