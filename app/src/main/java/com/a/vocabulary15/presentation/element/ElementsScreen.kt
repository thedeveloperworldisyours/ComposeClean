package com.a.vocabulary15.presentation.element

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.MainActivity
import com.a.vocabulary15.presentation.common.ViewState
import com.a.vocabulary15.presentation.ui.composables.GroupElementText
import com.a.vocabulary15.presentation.util.Screen

@Composable
fun ElementScreen(
    activity: MainActivity,
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
                activity,
                viewModel,
                (state.value as ViewState.Success<*>).value as List<Element>,
                navController
            )
        }
        else -> {
        }
    }
}

@Composable
fun ElementDataScreen(
    activity: MainActivity,
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
                        text = viewModel.elementName
                    )
                }, actions = {
                    IconButton(onClick = {
                        navController.navigate(
                            Screen.TestScreen.route +
                                "?idGroup=${viewModel.idGroup}&elementName=${viewModel.elementName}")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check_knowledge),
                            contentDescription = stringResource(id = R.string.check_your_knowledge)
                        )
                    }
                    IconButton(onClick = {
                        viewModel.isDeleteAllOpen = true
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
                    viewModel.isAddElementOpen = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = ""
                )
            }
        }
    ) {
        DetailDialog(viewModel)
        DeleteAllDialog(navController, viewModel)
        AddElementDialog(activity, viewModel)
        EditElementDialog(activity, viewModel)
        DeleteElementDialog(viewModel)
        Box(
            Modifier
                .fillMaxSize()
        ) {
            if (elements.isEmpty()) {
                viewModel.isAddElementOpen = true
                GroupElementText(
                    text = stringResource(id = R.string.empty_group, viewModel.elementName),
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
                    itemsIndexed(items = elements) { _, item: Element ->
                        ElementListItem(item, clickableItem = {
                            viewModel.item = item
                            viewModel.isDetailElementOpen = true
                        })
                    }
                }
            }
        }
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