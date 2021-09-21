package com.a.vocabulary15.presentation.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.presentation.ui.theme.Typography

@Composable
fun GroupElementText(text: String, modifier: Modifier) {
    Text(
        text = text, style = Typography.body1,
        modifier = modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun GroupElementTextPreview(){
    AddGroupButton{}
}