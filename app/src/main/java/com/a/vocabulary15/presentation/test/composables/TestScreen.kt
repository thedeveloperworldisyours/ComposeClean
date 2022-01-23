package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.a.vocabulary15.R
import com.a.vocabulary15.presentation.test.TestViewModel
import com.a.vocabulary15.presentation.ui.composables.GroupElementText

@Composable
fun TestScreen(
    navController: NavController,
    viewModel: TestViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    if (state.elements.isEmpty()) {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            GroupElementText(
                text = stringResource(id = R.string.empty_group, viewModel.elementName),
                modifier = Modifier
                    .align(
                        Alignment.Center
                    )
            )
        }
    } else {
        TestChooseLevelDialog(viewModel)
        TestFinishedDialog(navController, viewModel)
        TestMainColumn(
            viewModel,
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
