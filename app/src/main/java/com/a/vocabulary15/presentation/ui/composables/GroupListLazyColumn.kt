package com.a.vocabulary15.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.presentation.ui.theme.Typography

@Composable
fun GroupListLazyColumn(modifier: Modifier, names: List<Group>, clickableItem: (Int)->Unit){
    LazyColumn(modifier = modifier) {
        itemsIndexed(items = names) { _, item: Group ->
            Surface(modifier = Modifier.clickable { clickableItem(item.id) }){
            Text(text = item.name, modifier = Modifier.fillMaxWidth().padding(5.dp), style = Typography.body1)
            Divider()
            }
        }
    }
}