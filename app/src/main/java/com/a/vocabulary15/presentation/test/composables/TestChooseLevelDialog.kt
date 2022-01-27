package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.a.vocabulary15.R
import com.a.vocabulary15.presentation.test.TestEvent
import com.a.vocabulary15.presentation.test.TestViewModel
import com.a.vocabulary15.util.TestTags

@Composable
fun TestChooseLevelDialog(
    viewModel: TestViewModel) {
    if (viewModel.state.value.isChooseLevelOpen) {
        Dialog(onDismissRequest = { viewModel.onEvent(TestEvent.OpenChooseMode(false)) }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .testTag(TestTags.CHOOSE_LEVEL_DIALOG),
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = stringResource(id = R.string.test_level),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = stringResource(id = R.string.test_level_choose),
                        color = Color.Black,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.padding(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                viewModel.onEvent(TestEvent.ChangeMode(TestViewModel.LIST_MODE))
                                viewModel.onEvent(TestEvent.OpenChooseMode(false))
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(40.dp)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                .testTag(TestTags.LIST_LEVEL_BUTTON),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.test_level_list),
                                color = Color.White
                            )
                        }
                        Button(
                            onClick = {
                                viewModel.onEvent(TestEvent.ChangeMode(TestViewModel.TEXT_MODE))
                                viewModel.onEvent(TestEvent.OpenChooseMode(false))
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(40.dp)
                                .testTag(TestTags.ELEMENT_LEVEL_BUTTON),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.test_level_text),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}