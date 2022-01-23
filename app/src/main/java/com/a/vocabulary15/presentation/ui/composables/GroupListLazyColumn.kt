package com.a.vocabulary15.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.presentation.ui.theme.Typography
import com.a.vocabulary15.presentation.util.Screen

@Composable
fun GroupListLazyColumn(
    modifier: Modifier,
    names: List<Group>,
    navController: NavController
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp),
        modifier = modifier
    ) {
        items(items = names) { item: Group ->
            Surface(modifier = Modifier.clickable { navController.navigate(Screen.ElementScreen.route +
            "?idGroup=${item.id}&elementName=${item.name}") }) {
                Card(
                    backgroundColor = Color.Blue,
                    elevation = 5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = item.name,
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
    }
}