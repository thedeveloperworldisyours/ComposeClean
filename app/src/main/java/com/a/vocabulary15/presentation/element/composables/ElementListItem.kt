package com.a.vocabulary15.presentation.element.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.ui.theme.Typography

@Composable
fun ElementListItem(
    item: Element,
    clickableItem: () -> Unit
) {
    Surface(modifier = Modifier.clickable { clickableItem() }) {
        Card(
            backgroundColor = Color.Blue,
            elevation = 5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Box {
                Text(
                    text = item.value,
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
                    Image(
                        modifier = Modifier
                            .size(50.dp),
                        painter = rememberImagePainter(item.image),
                        contentDescription = null
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