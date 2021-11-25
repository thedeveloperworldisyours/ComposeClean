package com.a.vocabulary15.presentation.element


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.ElementsActivity
import com.a.vocabulary15.presentation.ui.theme.Typography

@Composable
fun EditElementDialog(activity: ElementsActivity, element: Element) {
    if (activity.viewModel.isEditElementOpen.value) {
        Dialog(onDismissRequest = { activity.viewModel.isDeleteElementOpen.value = false }) {
            val link = stringResource(id =R.string.word_reference)
            val inputValue = remember { mutableStateOf(element.value) }
            val inputValueLink = remember { mutableStateOf(element.link) }
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
                        text = stringResource(R.string.add_element),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    TextField(
                        value = inputValue.value,
                        maxLines = 1,
                        onValueChange = {inputValue.value = it},
                        placeholder = { Text(text = stringResource(id = R.string.enter_name)) },
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth()
                    )
                    TextField(
                        value = inputValueLink.value,
                        maxLines = 1,
                        onValueChange = { inputValueLink.value = it},
                        placeholder = { Text(text = stringResource(id = R.string.enter_link)) },
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth()
                    )
                    Button(onClick= {
                        ContextCompat.startActivity(
                            activity,
                            Intent(Intent.ACTION_VIEW, Uri.parse(link)),
                            null
                        )
                    },
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.search_word_link),
                            color = MaterialTheme.colors.secondary,
                            style = Typography.button
                        )
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                activity.viewModel.isEditElementOpen.value = false
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
                                activity.viewModel.isEditElementOpen.value = false
                                activity.viewModel.editElement(
                                    Element(
                                        id = element.id,
                                        groupId = activity.idGroup.toInt(),
                                        value = inputValue.value,
                                        image = "",//returnImageLink,
                                        link = inputValueLink.value
                                    )
                                )
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(60.dp)
                                .padding(10.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(id = android.R.color.transparent))
                        ) {
                            Text(
                                text = stringResource(id = R.string.accept),
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