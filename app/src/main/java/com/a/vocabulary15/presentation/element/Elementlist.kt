package com.a.vocabulary15.presentation.element

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.R
import com.a.vocabulary15.presentation.ElementsActivity
import com.a.vocabulary15.presentation.ui.theme.Typography

@Composable
fun ElementList(
    activity: ElementsActivity,
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
            Text(
                text = activity.viewModel.item.value,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp, 16.dp, 0.dp, 16.dp),
                style = Typography.body1
            )
        }
    }
}