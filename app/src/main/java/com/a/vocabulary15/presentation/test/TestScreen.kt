package com.a.vocabulary15.presentation.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.ui.composables.GroupElementText

@Composable
fun FirstContentScreen(
    activity: TestActivity,
    listItems: List<Element>
) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = activity.elementName)
                }
            )
        }
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

            TestLazyColumn(
                activity,
                listItems
            )
        }
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