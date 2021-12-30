package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.test.TestActivity
import com.a.vocabulary15.presentation.test.TestEvent
import com.a.vocabulary15.presentation.test.TestViewModel.Companion.LIST_MODE
import com.a.vocabulary15.presentation.test.TestViewModel.Companion.TEXT_MODE

@Composable
fun TestMainColumn(
    activity: TestActivity,
    listItems: List<Element>
) {
    Column {
        TestScoreCard(
            listItems[activity.viewModel.randomNumber.value].image,
            activity.viewModel.right.value,
            activity.viewModel.wrong.value
        )
        when (activity.viewModel.mode.value) {
            LIST_MODE -> {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    itemsIndexed(items = listItems) { _, item: Element ->
                        TestListItem(item) {
                            if ((listItems[activity.viewModel.randomNumber.value].id == item.id)) {
                                activity.viewModel.nextElement()
                            } else {
                                activity.viewModel.onEvent(
                                    TestEvent.ChangeWrong(
                                        activity.viewModel.wrong.value + 1
                                    )
                                )
                            }
                        }
                    }
                }
            }
            TEXT_MODE -> {
                TestRespondsTextField(label = stringResource(id = R.string.test_respond),
                    value = activity.viewModel.text.value,
                    onValueChange = { activity.viewModel.onEvent(TestEvent.ChangeText(it)) })
                Button(
                    onClick = {
                        if (listItems[activity.viewModel.randomNumber.value].value ==
                            activity.viewModel.text.value
                        ) {
                            activity.viewModel.onEvent(TestEvent.ChangeText(""))
                            activity.viewModel.nextElement()
                        } else {
                            activity.viewModel.onEvent(
                                TestEvent.ChangeWrong(
                                    activity.viewModel.wrong.value + 1))
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
                activity.viewModel.onEvent(TestEvent.OpenChooseMode(true))
            }
        }
    }
}