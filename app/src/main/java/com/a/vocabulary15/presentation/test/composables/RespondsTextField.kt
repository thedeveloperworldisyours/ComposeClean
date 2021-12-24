package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun RespondsTextField(
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        keyboardOptions = keyboardOptions
    )
}