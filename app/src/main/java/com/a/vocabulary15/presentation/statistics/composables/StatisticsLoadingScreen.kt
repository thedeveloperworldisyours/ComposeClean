package com.a.vocabulary15.presentation.statistics.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.R
import com.a.vocabulary15.presentation.statistics.StatisticsEvent
import com.a.vocabulary15.presentation.statistics.StatisticsViewModel


@Composable
fun StatisticsLoadingScreen(
        viewModel: StatisticsViewModel
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
                viewModel.onEvent(StatisticsEvent.FetchStatistics)
            }
        }
    }
