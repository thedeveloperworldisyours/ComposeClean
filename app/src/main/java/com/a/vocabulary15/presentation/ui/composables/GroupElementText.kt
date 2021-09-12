package com.a.vocabulary15.presentation.ui.composables

import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun GroupElementText(text: String) {
    Text(
        text= text,
        style = typography.h6
    )
}