package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.test.TestActivity
import com.a.vocabulary15.presentation.ui.composables.GroupElementText

@Composable
fun TestScreen(
    activity: TestActivity
) {
    val state = activity.viewModel.state.value
    if (state.elements.isEmpty()) {
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
        TestChooseLevelDialog(activity)
        TestFinishedDialog(activity)
        TestMainColumn(
            activity,
            state.elements
        )
    }
}

/*
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
}*/
