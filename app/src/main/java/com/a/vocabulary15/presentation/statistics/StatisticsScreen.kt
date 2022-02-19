package com.a.vocabulary15.presentation.statistics

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Statistics
import com.a.vocabulary15.presentation.ui.theme.Typography
import com.a.vocabulary15.presentation.util.convertMillisecondsToCalendar
import com.a.vocabulary15.presentation.util.findFinalScoreColor
import com.a.vocabulary15.util.TestTags
import java.util.*

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val statistics = viewModel.statistics.collectAsState(initial = emptyList())
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (statistics.value.isEmpty()) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.statistics_empty),
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body1,
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.ELEMENT_LIST)
                ) {

                    items(items = statistics.value) { item: Statistics ->
                        Surface(modifier = Modifier.clickable { }) {
                            Card(
                                backgroundColor = Color.Blue,
                                elevation = 5.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 8.dp)
                            ) {
                                Box {
                                    Text(
                                        text = convertMillisecondsToCalendar(
                                            Calendar.getInstance(),
                                            item.date
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .padding(25.dp, 16.dp, 0.dp, 16.dp),
                                        style = Typography.body1
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .align(Alignment.CenterEnd)
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
                                                .padding(25.dp, 16.dp, 0.dp, 16.dp),
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
        }
    }
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
}

@Composable
fun StatisticsLoadingScreen(
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
}*/
