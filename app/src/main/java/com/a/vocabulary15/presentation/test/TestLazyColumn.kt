package com.a.vocabulary15.presentation.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.domain.model.Element

@Composable
fun TestLazyColumn(
    activity: TestActivity,
    listItems: List<Element>
) {
    Column {
        ScoreCard(
            listItems[activity.viewModel.randomNumber].image,
            activity.viewModel.right.toString(),
            activity.viewModel.wrong)
        LazyColumn(
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
    }
}