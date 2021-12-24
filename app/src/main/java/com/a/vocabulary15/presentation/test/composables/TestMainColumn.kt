package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.test.TestActivity
import com.a.vocabulary15.presentation.test.TestViewModel.Companion.LIST_MODE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TestMainColumn(
    activity: TestActivity,
    listItems: List<Element>, scope: CoroutineScope, scaffoldState: ScaffoldState
) {
    Column {
        ScoreCard(
            listItems[activity.viewModel.randomNumber].image,
            activity.viewModel.right,
            activity.viewModel.wrong
        )
        if (activity.mode == LIST_MODE) {
            /*LazyColumn(
                contentPadding = PaddingValues(bottom = 80.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                itemsIndexed(items = listItems) { _, item: Element ->
                    TestListItem(item) {
                        if ((listItems[activity.viewModel.randomNumber].id == item.id)) {
                            activity.viewModel.nextElement()
                        } else {
                            activity.viewModel.onWrongChange(activity.viewModel.wrong + 1)
                        }
                    }
                }
            }
        } else {*/
            RespondsTextField(label = stringResource(id = R.string.test_respond),
                value = activity.viewModel.text,
                onValueChange = { activity.viewModel.onTextChanged(it) })
            Button(
                onClick = {
                    if (listItems[activity.viewModel.randomNumber].value == activity.viewModel.text) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(":)${activity.viewModel.text}")
                        }
                        activity.viewModel.onTextChanged("")
                        activity.viewModel.nextElement()
                    } else {
                        activity.viewModel.onWrongChange(activity.viewModel.wrong + 1)
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = stringResource(id = R.string.test_submit))
            }
        }
    }
}