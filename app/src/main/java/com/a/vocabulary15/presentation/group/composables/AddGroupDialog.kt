package com.a.vocabulary15.presentation.group

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.util.TestTags
import com.a.vocabulary15.util.TestTags.NEW_GROUP_DIALOG

@Composable
fun AddGroupDialog(viewModel: MainViewModel) {
    if (viewModel.state.value.isAddGroupOpen) {
        Dialog(onDismissRequest = { viewModel.onEvent(GroupEvent.OpenAddGroupDialog(false)) }) {
            val inputValue = rememberSaveable { mutableStateOf("") }
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .padding(5.dp).testTag(NEW_GROUP_DIALOG),
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = stringResource(R.string.add_group),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    TextField(
                        value = inputValue.value,
                        maxLines = 1,
                        onValueChange = { inputValue.value = it },
                        placeholder = { Text(text = stringResource(id = R.string.enter_name)) },
                        modifier = Modifier
                            .padding(0.dp, 16.dp)
                            .fillMaxWidth()
                            .testTag(TestTags.NAME_TEXT_FIELD)
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                viewModel.onEvent(GroupEvent.OpenAddGroupDialog(false))
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(60.dp)
                                .padding(10.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.close),
                                color = Color.White
                            )
                        }
                        Button(
                            onClick = {
                                viewModel.onEvent(GroupEvent.OpenAddGroupDialog(false))
                                viewModel.insertGroup(Group(0, inputValue.value))
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(60.dp)
                                .padding(10.dp, 10.dp, 0.dp, 10.dp)
                                .testTag(TestTags.SAVE_GROUP),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.add),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}