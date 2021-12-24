package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
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
import com.a.vocabulary15.presentation.test.TestActivity
import com.a.vocabulary15.presentation.ui.composables.GroupElementText
import kotlinx.coroutines.CoroutineScope

@Composable
fun TestScreen(
    activity: TestActivity, scope: CoroutineScope, scaffoldState: ScaffoldState
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
            val list = (groupElementStates as GroupElementStates.Data<*>).data as List<Element>
            FirstContentScreen(activity, list, scope, scaffoldState)
        }
        else -> {
        }
    }
}

@Composable
fun FirstContentScreen(
    activity: TestActivity,
    listItems: List<Element>, scope: CoroutineScope, scaffoldState: ScaffoldState
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
        TestFinishedDialog(activity)
        TestMainColumn(
            activity,
            listItems,
            scope,
            scaffoldState
        )
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