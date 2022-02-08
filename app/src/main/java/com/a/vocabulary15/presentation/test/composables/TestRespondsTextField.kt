package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.test.TestViewModel

@Composable
fun TestRespondsTextField(
    done: () -> Unit,
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val focusRequester = remember() { FocusRequester() }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            done()
        })
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}