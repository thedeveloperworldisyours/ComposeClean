package com.a.vocabulary15.presentation.ui.composables

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun AddGroupButton(onClick: () -> Unit){
    Button(onClick = onClick) {
        Text(text = "Add Group")
    }
}