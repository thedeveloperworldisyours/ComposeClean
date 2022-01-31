package com.a.vocabulary15.presentation.element.composables

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.common.ViewState
import com.a.vocabulary15.presentation.element.ElementEvent
import com.a.vocabulary15.presentation.element.ElementsViewModel
import com.a.vocabulary15.presentation.ui.composables.GroupElementText
import com.a.vocabulary15.presentation.util.Screen
import com.a.vocabulary15.util.TestTags

@Composable
fun ElementScreen(
    context: Context,
    navController: NavController,
    viewModel: ElementsViewModel = hiltViewModel()
) {
    val state = viewModel.viewState.collectAsState(initial = ViewState.Loading)
    when (state.value) {
        is ViewState.Loading -> {
            ElementLoadingScreen(
                viewModel
            )
        }
        is ViewState.Success<*> -> {
            ElementDataScreen(
                context,
                viewModel,
                (state.value as ViewState.Success<*>).value as List<Element>,
                navController
            )
        }
    }
}

@Composable
fun ElementDataScreen(
    context: Context,
    viewModel: ElementsViewModel,
    elements: List<Element>,
    navController: NavController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = viewModel.state.value.elementName
                    )
                }, actions = {
                    IconButton(
                        modifier = Modifier.testTag(TestTags.CHECK_YOUR_KNOWLEDGE),
                        onClick = {
                            navController.navigate(
                                Screen.TestScreen.route +
                                        "?idGroup=${viewModel.state.value.idGroup}&elementName=${viewModel.state.value.elementName}"
                            )
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check_knowledge),
                            contentDescription = stringResource(id = R.string.check_your_knowledge)
                        )
                    }
                    IconButton(onClick = {
                        viewModel.onEvent(ElementEvent.OpenDeleteAllDialog(true))
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
                modifier = Modifier.testTag(TestTags.NEW_ELEMENT),
                onClick = {
                    viewModel.onEvent(ElementEvent.SetInputValue(""))
                    viewModel.onEvent(ElementEvent.SetInputValueLink(""))
                    viewModel.onEvent(ElementEvent.SetInputValueLinkImage(""))
                    viewModel.onEvent(ElementEvent.OpenAddElementDialog(true))
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = TestTags.NEW_ELEMENT
                )
            }
        }
    ) {

        Box(
            Modifier
                .fillMaxSize()
        ) {
            if (elements.isEmpty()) {
                GroupElementText(
                    text = stringResource(
                        id = R.string.empty_group,
                        viewModel.state.value.elementName
                    ),
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                        .testTag(TestTags.EMPTY_ELEMENT_MESSAGE)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.ELEMENT_LIST)
                ) {
                    itemsIndexed(items = elements) { _, item: Element ->
                        ElementListItem(item, clickableItem = {
                            viewModel.onEvent(ElementEvent.SetElement(item))
                            viewModel.onEvent(ElementEvent.OpenDetailElementDialog(true))
                        })
                    }
                }
            }
        }
        DetailDialog(viewModel)
        DeleteAllDialog(navController, viewModel)
        AddElementDialog(context, viewModel)
        EditElementDialog(context, viewModel)
        DeleteElementDialog(viewModel)
    }
}

@Composable
fun ElementLoadingScreen(
    viewModel: ElementsViewModel
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
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.blue_200),
                modifier = Modifier
                    .absolutePadding(8.dp, 8.dp, 8.dp, 8.dp)
                    .width(50.dp)
            )
            viewModel.onEvent(ElementEvent.FetchElements)
        }
    }
}