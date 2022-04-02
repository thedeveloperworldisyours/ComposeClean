package com.a.vocabulary15.presentation.statistics.composables

import android.graphics.Point
import android.widget.Toast
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
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
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

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
typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var icon: Int, var title: String, var screen: () -> Unit) {
    class Statistics(viewModel: StatisticsViewModel, title: String) : TabItem(R.drawable.ic_baseline_list_24,
        title,
        { })//viewModel.onEvent(StatisticsEvent.FetchStatistics) })

    class StatisticsByMonth(viewModel: StatisticsViewModel, title: String) :
        TabItem(R.drawable.ic_baseline_bar_chart,
            title, { })//viewModel.onEvent(StatisticsEvent.FetchStatisticsByMonth) })
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun Tabs(viewModel: StatisticsViewModel, tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    // OR ScrollableTabRow()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = colorResource(id = R.color.blue_200),
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
        }) {
        tabs.forEachIndexed { index, tab ->
            // OR Tab()
            LeadingIconTab(
                icon = { Icon(painter = painterResource(id = tab.icon), contentDescription = "") },
                text = { Text(tab.title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        if (index == 0) {
                            viewModel.onEvent(StatisticsEvent.FetchStatistics)
                        } else {
                            viewModel.onEvent(StatisticsEvent.FetchStatisticsByMonth)
                        }
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomScaffold(viewModel: StatisticsViewModel, list: List<*>) {
    val tabs = listOf(
        TabItem.Statistics(viewModel, stringResource(id = R.string.statistics)),
        TabItem.StatisticsByMonth(viewModel, stringResource(id = R.string.statistics_by_month))

    )
    val pagerState = rememberPagerState()
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
                Tabs(viewModel = viewModel, tabs = tabs, pagerState = pagerState)
                TabsContent(tabs = tabs, pagerState = pagerState)
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
    BarChart(countList)
    /*LazyColumn(
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
    }*/
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


/*private fun identifyCLickItem(
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
}*/

private fun getListPoints(countList: List<Int>): List<Point> {
    val points = mutableListOf<Point>()
    var x = 10
    for( point in countList) {
        points.add(Point(x, point))
        x += 80
    }
    return points
}

@Composable
fun BarChart(countList: List<Int>) {
    val point = getListPoints(countList)

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
                        /*val i = identifyCLickItem(point, it.x, it.y)
                        Toast
                            .makeText(context, "onTap: $i", Toast.LENGTH_SHORT)
                            .show()*/
                    }
                )
            }
    ) {
        drawLine(
            start = Offset(10f, 300f),
            end = Offset(10f, 0f),
            color = Color.Black,
            strokeWidth = 3f
        )
        drawLine(
            start = Offset(10f, 300f),
            end = Offset(900f, 300f),
            color = Color.Black,
            strokeWidth = 3f
        )
        start = true

        for (p in point) {
            drawRect(
                color = Color.Red,
                topLeft = Offset(p.x + 30f, 300 - p.y * heightPre),
                size = Size(55f, p.y * heightPre)
            )
        }
    }
}
