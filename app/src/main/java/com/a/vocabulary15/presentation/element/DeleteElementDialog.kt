package com.a.vocabulary15.presentation.element

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.ElementsActivity

@Composable
fun DeleteElementDialog(
    activity: ElementsActivity,
    element: Element
) {
    if (activity.viewModel.isDeleteElementOpen.value) {
        Dialog(onDismissRequest = { activity.viewModel.isDeleteElementOpen.value = false }) {
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
                        text = stringResource(R.string.delete_title),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = stringResource(R.string.do_you_want_to_delete, element.value),
                        color = Color.Black,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                activity.viewModel.isDeleteElementOpen.value = false
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(60.dp)
                                .padding(10.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(id = android.R.color.transparent))
                        ) {
                            Text(
                                text = stringResource(id = R.string.close),
                                color = Color.Gray
                            )
                        }
                        Button(
                            onClick = {
                                activity.viewModel.deleteElement(element.id)
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(60.dp)
                                .padding(10.dp, 10.dp, 0.dp, 10.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(id = android.R.color.transparent))
                        ) {
                            Text(
                                text = stringResource(id = R.string.delete),
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}