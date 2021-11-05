package com.a.vocabulary15.presentation.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.R
import com.a.vocabulary15.presentation.ui.theme.Typography

@Composable
fun AddGroupButton(onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.add_group),
            color = MaterialTheme.colors.secondary, style = Typography.button)
    }
}

@Preview
@Composable
fun AddGroupButtonPreview(){
    AddGroupButton{}
}