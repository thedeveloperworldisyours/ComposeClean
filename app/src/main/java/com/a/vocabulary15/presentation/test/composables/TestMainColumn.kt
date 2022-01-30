package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.test.TestEvent
import com.a.vocabulary15.presentation.test.TestViewModel
import com.a.vocabulary15.presentation.test.TestViewModel.Companion.LIST_MODE
import com.a.vocabulary15.presentation.test.TestViewModel.Companion.TEXT_MODE
import com.a.vocabulary15.util.TestTags

@Composable
fun TestMainColumn(
    viewModel: TestViewModel,
    listItems: List<Element>
) {
    Column {
        TestScoreCard(
            listItems[viewModel.state.value.randomNumber].image,
            viewModel.state.value.right,
            viewModel.state.value.wrong
        )
        when (viewModel.state.value.levelMode) {
            LIST_MODE -> {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.ELEMENT_LIST_OPTIONS)
                ) {
                    itemsIndexed(items = listItems) { _, item: Element ->
                        TestListItem(item) {
                            if ((listItems[viewModel.state.value.randomNumber].id == item.id)) {
                                viewModel.nextElement()
                            } else {
                                viewModel.onEvent(
                                    TestEvent.ChangeWrong(
                                        viewModel.state.value.wrong + 1
                                    )
                                )
                            }
                        }
                    }
                }
            }
            TEXT_MODE -> {
                TestRespondsTextField(label = stringResource(id = R.string.test_respond),
                    value = viewModel.state.value.text,
                    onValueChange = { viewModel.onEvent(TestEvent.ChangeText(it)) })
                Button(
                    onClick = {
                        if (listItems[viewModel.state.value.randomNumber].value ==
                            viewModel.state.value.text
                        ) {
                            viewModel.onEvent(TestEvent.ChangeText(""))
                            viewModel.nextElement()
                        } else {
                            viewModel.onEvent(
                                TestEvent.ChangeWrong(
                                    viewModel.state.value.wrong + 1
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp)
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.test_submit))
                }
            }
            else -> {
                viewModel.onEvent(TestEvent.OpenChooseMode(true))
            }
        }
    }
}