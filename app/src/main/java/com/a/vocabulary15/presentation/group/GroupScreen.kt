package com.a.vocabulary15.presentation.group

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.a.vocabulary15.R
import com.a.vocabulary15.presentation.ui.composables.GroupListLazyColumn

@ExperimentalAnimationApi
@Composable
fun GroupScreen(
    viewModel: MainViewModel = hiltViewModel(),
    itemClickable: (Int, String) -> Unit
) {
    val groups = viewModel.groups.collectAsState(initial =  emptyList())
    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontWeight = FontWeight.Bold, text = stringResource(id = R.string.app_name)
                    )
                }
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.isAddGroupOpen = true }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = ""
                )
            }
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            AddGroupDialog(viewModel)
            GroupListLazyColumn(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                groups.value,
                itemClickable
            )
        }
    }
}