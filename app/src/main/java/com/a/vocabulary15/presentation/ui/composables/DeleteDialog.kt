package com.a.vocabulary15.presentation.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.a.vocabulary15.R

@Composable
fun ShowAlertDialog(isDialogOpen: MutableState<Boolean>, delete: () -> Unit) {

    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .height(450.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = stringResource(id = R.string.delete),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                    Spacer(modifier = Modifier.padding(15.dp))
                    Text(
                        text = stringResource(id = R.string.do_you_want_delete),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                    Spacer(modifier = Modifier.padding(15.dp))

                    Row {


                        Button(
                            onClick = {
                                isDialogOpen.value = false
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(60.dp)
                                .padding(10.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(Color.Gray)
                        ) {
                            Text(
                                text = stringResource(id = R.string.close),
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                        Button(
                            onClick = {
                                delete()
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(60.dp)
                                .padding(10.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(Color.Gray)
                        ) {
                            Text(
                                text = stringResource(id = R.string.delete),
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }

}