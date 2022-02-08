package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.presentation.ui.theme.Typography

@Composable
fun TestCharItem(text: String, backgroundColor: Color) {
    Surface {
        Card(
            backgroundColor = backgroundColor,
            elevation = 5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Box {
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .width(16.dp)
                        .align(Alignment.CenterStart)
                        .padding(4.dp, 4.dp, 0.dp, 4.dp),
                    style = Typography.body1
                )
            }
        }
    }
}