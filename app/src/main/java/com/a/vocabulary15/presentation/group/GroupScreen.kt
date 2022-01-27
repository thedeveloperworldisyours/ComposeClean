package com.a.vocabulary15.presentation.group

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.a.vocabulary15.R
import com.a.vocabulary15.presentation.ui.composables.GroupElementText
import com.a.vocabulary15.presentation.ui.composables.GroupListLazyColumn
import com.a.vocabulary15.util.TestTags
import com.a.vocabulary15.util.TestTags.GROUP_LIST
import com.a.vocabulary15.util.TestTags.NEW_GROUP

@ExperimentalAnimationApi
@Composable
fun GroupScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val groups = viewModel.groups.collectAsState(initial = emptyList())
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
                onClick = { viewModel.onEvent(GroupEvent.OpenAddGroupDialog(true)) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = NEW_GROUP
                )
            }
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            AddGroupDialog(viewModel)
            if (groups.value.isEmpty()) {
                GroupElementText(
                    text = stringResource(id = R.string.empty_group_list),
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                        .testTag(TestTags.EMPTY_GROUP_MESSAGE)
                )
            } else {
                GroupListLazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .testTag(GROUP_LIST),
                    groups.value,
                    navController,
                )
            }
        }
    }
}