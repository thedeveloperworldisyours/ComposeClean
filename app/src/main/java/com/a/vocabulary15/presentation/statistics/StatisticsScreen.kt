package com.a.vocabulary15.presentation.statistics

import android.graphics.Point
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.a.vocabulary15.R

@Composable
fun StatisticsScreen(
    navController: NavController) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontWeight = FontWeight.Bold, text = stringResource(id = R.string.statistics)
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
            BarChart()
        }
    }
}

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