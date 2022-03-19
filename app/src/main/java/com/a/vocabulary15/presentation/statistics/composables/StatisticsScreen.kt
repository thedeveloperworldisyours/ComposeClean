package com.a.vocabulary15.presentation.statistics.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.a.vocabulary15.R
import com.a.vocabulary15.presentation.common.ViewState
import com.a.vocabulary15.presentation.statistics.StatisticsEvent
import com.a.vocabulary15.presentation.statistics.StatisticsViewModel
import com.a.vocabulary15.presentation.statistics.data.StatisticsEntity
import com.a.vocabulary15.presentation.ui.theme.Typography
import com.a.vocabulary15.presentation.util.findFinalScoreColor
import com.a.vocabulary15.util.TestTags

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val state = viewModel.viewState.collectAsState(initial = ViewState.Loading)

    when (state.value) {
        is ViewState.Loading -> {
            StatisticsLoadingScreen(viewModel)
        }
        is ViewState.Success -> {
            CustomScaffold(viewModel, (state.value as ViewState.Success<*>).value as List<*>)
        }
    }
}

@Composable
fun CustomScaffold(viewModel: StatisticsViewModel, list: List<*>) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = stringResource(id = R.string.statistics)
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (list.isEmpty()) {
                EmptyDataScreen()
            } else {
                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val (all, by) = createRefs()
                    Button(
                        onClick = {
                            viewModel.onEvent(StatisticsEvent.FetchStatistics)
                        },
                        modifier = Modifier
                            .width(90.dp)
                            .height(60.dp)
                            .padding(10.dp)
                            .constrainAs(all) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            },
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.statistics_all),
                            color = Color.White
                        )
                    }
                    Button(
                        onClick = {
                            viewModel.onEvent(StatisticsEvent.FetchStatisticsByMonth)
                        },
                        modifier = Modifier
                            .width(90.dp)
                            .height(60.dp)
                            .padding(10.dp, 10.dp, 0.dp, 10.dp)
                            .testTag(TestTags.SAVE_GROUP).constrainAs(by) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            },
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.statistics_by_month),
                            color = Color.White
                        )
                    }
                }
                if (list[0] is StatisticsEntity) {
                    StatisticsEntityDataScreen(list as List<StatisticsEntity>)
                } else {
                    StatisticsMonthScreen(list as List<Int>)
                }
            }
        }
    }
}

@Composable
fun StatisticsMonthScreen(countList: List<Int>) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TestTags.STATISTICS_LIST)
    ) {
        itemsIndexed(items = countList) { index: Int, number: Int ->
            Surface(modifier = Modifier.clickable { }) {
                Card(
                    backgroundColor = Color.Blue,
                    elevation = 5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                        val (title, row) = createRefs()
                        Text(
                            text = "$index months ago",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .padding(16.dp, 8.dp, 0.dp, 8.dp)
                                .constrainAs(title) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                },
                            style = Typography.body1
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .constrainAs(row) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                }
                                .background(Color.White)
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(55.dp),
                                painter = painterResource(id = R.drawable.ic_arrow),
                                contentDescription = null
                            )
                            Text(
                                text = number.toString(),
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(0.dp, 16.dp, 0.dp, 16.dp),
                                style = Typography.body1
                            )
                            Spacer(
                                modifier = Modifier
                                    .padding(13.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun StatisticsEntityDataScreen(statistics: List<StatisticsEntity>) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TestTags.STATISTICS_LIST)
    ) {
        items(items = statistics) { item: StatisticsEntity ->
            Surface(modifier = Modifier.clickable { }) {
                Card(
                    backgroundColor = Color.Blue,
                    elevation = 5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                        val (time, title, row) = createRefs()
                        Text(
                            text = item.date,
                            color = Color.White,
                            modifier = Modifier
                                .padding(16.dp, 8.dp, 0.dp, 8.dp)
                                .constrainAs(time) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                },
                            style = Typography.caption
                        )
                        Text(
                            text = item.groupTitle,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .padding(16.dp, 8.dp, 0.dp, 8.dp)
                                .constrainAs(title) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(time.end)
                                },
                            style = Typography.body1
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .constrainAs(row) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                }
                                .background(Color.White)
                        ) {

                            Image(
                                modifier = Modifier
                                    .size(55.dp),
                                painter = painterResource(id = R.drawable.ic_arrow),
                                contentDescription = null
                            )
                            Text(
                                text = item.points.toString(),
                                fontWeight = FontWeight.Bold,
                                color = findFinalScoreColor(item.points),
                                modifier = Modifier
                                    .padding(0.dp, 16.dp, 0.dp, 16.dp),
                                style = Typography.body1
                            )
                            Spacer(
                                modifier = Modifier
                                    .padding(13.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyDataScreen() {
    Text(
        fontWeight = FontWeight.Bold,
        text = stringResource(id = R.string.statistics_empty),
        modifier = Modifier
            .fillMaxSize(),
        style = Typography.body1,
    )
}

/*
private fun identifyCLickItem(
    points: List<Point>,
    x: Float,
    y: Float
): Int {
    for ((index, point) in points.withIndex()) {
        if (x > point.x + 20 && x < point.x + 20 + 40) {
            return index
        }
    }
    return -1
}

@Composable
fun BarChart() {
    val point = listOf(
        Point(10, 10),
        Point(90, 100),
        Point(170, 30),
        Point(250, 200),
        Point(330, 120),
        Point(410, 10),
        Point(490, 280),
        Point(570, 100),
        Point(650, 10),
        Point(730, 100),
        Point(810, 200),
    )

    val context = LocalContext.current
    var start by remember { mutableStateOf(false) }
    val heightPre by animateFloatAsState(
        targetValue = if (start) 1f else 0f,
        animationSpec = FloatTweenSpec(duration = 1000)
    )

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        val i = identifyCLickItem(point, it.x, it.y)
                        Toast
                            .makeText(context, "onTap: $i", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
            }
    ) {
        drawLine(
            start = Offset(10f, 600f),
            end = Offset(10f, 0f),
            color = Color.Black,
            strokeWidth = 3f
        )
        drawLine(
            start = Offset(10f, 600f),
            end = Offset(900f, 600f),
            color = Color.Black,
            strokeWidth = 3f
        )
        start = true

        for (p in point) {
            drawRect(
                color = Color.Red,
                topLeft = Offset(p.x + 30f, 600 - (600 - p.y) * heightPre),
                size = Size(55f, (600 - p.y) * heightPre)
            )
        }
    }
}*/
