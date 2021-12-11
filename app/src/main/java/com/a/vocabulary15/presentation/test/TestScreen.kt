package com.a.vocabulary15.presentation.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.presentation.ui.composables.GroupElementText

@Composable
fun TestScreen(
    activity: TestActivity
) {
    val groupElementStates: GroupElementStates<*> by activity.viewModel.genericLiveData.observeAsState(
        initial = GroupElementStates.Loading
    )
    when (groupElementStates) {
        is GroupElementStates.Loading -> {
            TestLoadingScreen(
                activity
            )
        }
        is GroupElementStates.Data -> {
            TestContentScreen(activity, groupElementStates)
        }
        else -> {
        }
    }
}

@Composable
fun TestContentScreen(
    activity: TestActivity,
    groupElementStates: GroupElementStates<*>
) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = activity.elementName)
                }
            )
        }
    ) {
        val listItems = (groupElementStates as GroupElementStates.Data<*>).data as List<Element>
        val completedList : MutableList<Boolean> = MutableList(listItems.size) {false}
        Box(
            Modifier
                .fillMaxSize()
        ) {
            if (listItems.isEmpty()) {
                GroupElementText(
                    text = stringResource(id = R.string.empty_group, activity.elementName),
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    itemsIndexed(items = listItems) { index, item: Element ->
                        TestListItem(completedList, index, item, clickableItem = {})
                    }
                }
            }
        }
    }
}

@Composable
fun TestLoadingScreen(activity: TestActivity) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.loading))
                }
            )
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