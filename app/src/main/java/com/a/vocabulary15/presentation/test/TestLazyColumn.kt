package com.a.vocabulary15.presentation.test

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.a.vocabulary15.domain.model.Element

@Composable
fun TestLazyColumn (
    activity: TestActivity,
listItems: List<Element>,
randomNumber: Int,
    newImage: String
){
    Column {
        Box(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center),
                painter = rememberImagePainter(newImage),
                contentDescription = null
            )
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
                        //newImage = listItems[randomNumber].image
                        Toast.makeText(
                            activity,
                            "stringResource(id = R.string.get_right)",
                            Toast.LENGTH_SHORT
                        ).show()
                        //Snackbar(content = { Text(text = "Get Right")})
                    } else {
                        Toast.makeText(
                            activity,
                            "stringResource(id = R.string.get_wrong)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}