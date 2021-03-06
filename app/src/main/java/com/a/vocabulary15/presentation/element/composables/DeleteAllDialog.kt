package com.a.vocabulary15.presentation.element.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.a.vocabulary15.R
import com.a.vocabulary15.presentation.element.ElementEvent
import com.a.vocabulary15.presentation.element.ElementsViewModel

@Composable
fun DeleteAllDialog(
    navController: NavController,
    viewModel: ElementsViewModel
) {
    if (viewModel.state.value.isDeleteAllOpen) {
        Dialog(onDismissRequest = { viewModel.onEvent(ElementEvent.OpenDeleteAllDialog(false)) }) {
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = stringResource(id = R.string.delete_title),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = stringResource(
                            id = R.string.do_you_want_delete,
                            viewModel.state.value.elementName
                        ),
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
                                viewModel.onEvent(ElementEvent.OpenDeleteAllDialog(false))
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(60.dp)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.close),
                                color = Color.White
                            )
                        }
                        Button(
                            onClick = {
                                viewModel.deleteGroupWithElements(viewModel.state.value.idGroup)
                                navController.navigateUp()
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(60.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.delete),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

}