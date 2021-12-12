package com.a.vocabulary15.presentation.test

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
            if ((groupElementStates as GroupElementStates.Data<*>).data is List<*>) {
                FirstContentScreen(activity, groupElementStates)
            } else {
                NextElementScreen(activity, groupElementStates)
            }
        }
        else -> {
        }
    }
}

@Composable
fun FirstContentScreen(
    activity: TestActivity,
    groupElementStates: GroupElementStates<*>
) {
    val listItems = (groupElementStates as GroupElementStates.Data<*>).data as List<Element>
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

        if (listItems.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
            ) {
                GroupElementText(
                    text = stringResource(id = R.string.empty_group, activity.elementName),
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                )
            }
        } else {
            val randomNumber = (listItems.indices).random()
            TestLazyColumn(activity, listItems, randomNumber, listItems[randomNumber].image)
        }
    }
}

@Composable
fun NextElementScreen(
    activity: TestActivity,
    groupElementStates: GroupElementStates<*>
) {
    val randomNumber = (groupElementStates as GroupElementStates.Data<*>).data as Int
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
        TestLazyColumn(activity, activity.viewModel.listItems, randomNumber, activity.viewModel.listItems[randomNumber].image)
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