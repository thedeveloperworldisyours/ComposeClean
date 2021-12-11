package com.a.vocabulary15.presentation.test

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.ui.theme.Typography

@Composable
fun TestListItem ( completedList : MutableList<Boolean>, index: Int, item: Element, clickableItem :()-> Unit) {
    val isSelected by rememberSaveable{ mutableStateOf(completedList[index])}
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
                RadioButton(
                    selected = isSelected,
                    onClick = clickableItem,
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd)
                        .background(Color.Blue)
                )
            }
        }
    }
}