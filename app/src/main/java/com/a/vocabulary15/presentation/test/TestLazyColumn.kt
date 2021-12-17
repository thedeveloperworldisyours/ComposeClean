package com.a.vocabulary15.presentation.test

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element

@Composable
fun TestLazyColumn(
    activity: TestActivity,
    listItems: List<Element>,
    randomNumber: Int,
    newImage: String
) {
    Column {
        Card(
            backgroundColor = Color.Blue,
            elevation = 5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Box(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(Color.Blue)
            ) {

                Row(
                    Modifier
                        .align(Alignment.Center)
                        .background(Color.White)
                ) {
                    Text(
                        modifier = Modifier
                            .background(Color.Blue)
                            .fillMaxHeight(),
                        text =
                        activity.viewModel.right.value.toString(),
                        fontSize = 35.sp,
                        color = Color.White
                    )
                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(Color.Blue),
                        painter = painterResource(id = R.drawable.ic_green_24),
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier
                            .size(50.dp),
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier
                            .size(55.dp),
                        painter = rememberImagePainter(newImage),
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier
                            .size(50.dp),
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null
                    )

                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(Color.Blue),
                        painter = painterResource(id = R.drawable.ic_red_24),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .background(Color.Blue)
                            .fillMaxHeight(),
                        text = activity.viewModel.wrong.value.toString(),
                        fontSize = 35.sp,
                        color = Color.White
                    )
                }
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            itemsIndexed(items = listItems) { _, item: Element ->
                TestListItem(item) {
                    if ((listItems[randomNumber].id == item.id)) {
                        activity.viewModel.nextElement()
                    } else {
                        activity.viewModel.wrong.value = activity.viewModel.wrong.value + 1
                    }
                }
            }
        }
    }
}