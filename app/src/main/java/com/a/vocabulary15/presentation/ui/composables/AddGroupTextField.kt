package com.a.vocabulary15.presentation.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddGroupTextField(text: String): String {
    val inputValue = remember { mutableStateOf(TextFieldValue()) }

    TextField(
        value = inputValue.value,
        maxLines = 1,
        onValueChange = { inputValue.value = it},
        placeholder = { Text(text = text) },
        modifier = Modifier
            .padding(0.dp, 16.dp)
            .fillMaxWidth()
    )

    return inputValue.value.text
}