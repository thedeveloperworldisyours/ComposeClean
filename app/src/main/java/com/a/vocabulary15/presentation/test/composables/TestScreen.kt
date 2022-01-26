package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.common.ViewState
import com.a.vocabulary15.presentation.test.TestViewModel
import com.a.vocabulary15.presentation.ui.composables.GroupElementText
import com.a.vocabulary15.util.TestTags

@Composable
fun TestScreen(
    navController: NavController,
    viewModel: TestViewModel = hiltViewModel()
) {
    val state = viewModel.viewState.collectAsState(initial = ViewState.Loading)
    when (state.value) {
        is ViewState.Loading -> {
            TestLoadingScreen(viewModel)
        }
        is ViewState.Success<*> -> {
            val elements = (state.value as ViewState.Success<*>).value as List<Element>
            if (elements.isEmpty()) {
                Box(
                    Modifier
                        .fillMaxSize()
                ) {
                    GroupElementText(
                        text = stringResource(id = R.string.empty_group, viewModel.elementName),
                        modifier = Modifier
                            .align(
                                Alignment.Center
                            ).testTag(TestTags.EMPTY_TEST)
                    )
                }
            } else {
                TestChooseLevelDialog(viewModel)
                TestFinishedDialog(navController, viewModel)
                TestMainColumn(
                    viewModel,
                    elements
                )
            }
        }
    }
}


@Composable
fun TestLoadingScreen(
    viewModel: TestViewModel) {
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
                text = viewModel.idGroup.toString(), modifier = Modifier
                    .align(
                        Alignment.Center
                    )
            )
        }
    }
}
