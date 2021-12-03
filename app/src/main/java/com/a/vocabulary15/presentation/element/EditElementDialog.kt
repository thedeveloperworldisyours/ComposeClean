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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.ElementsActivity
import com.a.vocabulary15.presentation.ui.theme.Typography

@Composable
fun EditElementDialog(activity: ElementsActivity) {
    if (activity.viewModel.isEditElementOpen.value) {
        Dialog(onDismissRequest = { activity.viewModel.isDeleteElementOpen.value = false }) {
            val link = stringResource(id = R.string.word_reference)
            val inputValue = remember { mutableStateOf(activity.viewModel.item.value) }
            val inputValueLink = remember { mutableStateOf(activity.viewModel.item.link) }
            val inputValueLinkImage = remember { mutableStateOf(activity.viewModel.item.image?: "") }
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
                        text = stringResource(R.string.edit_element),
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
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    )
                    TextField(
                        value = inputValueLink.value,
                        maxLines = 2,
                        onValueChange = { inputValueLink.value = it },
                        placeholder = { Text(text = stringResource(id = R.string.enter_link)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    )
                    Button(
                        onClick = {
                            ContextCompat.startActivity(
                                activity,
                                Intent(Intent.ACTION_VIEW, Uri.parse(link)),
                                null
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.search_word_link),
                            color = Color.White,
                        )
                    }
                    TextField(
                        value = inputValueLinkImage.value,
                        maxLines = 2,
                        onValueChange = { inputValueLinkImage.value = it },
                        placeholder = { Text(text = stringResource(id = R.string.enter_image_link)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    )
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
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.close),
                                color = Color.White
                            )
                        }
                        Button(
                            onClick = {
                                activity.viewModel.isEditElementOpen.value = false
                                activity.viewModel.editElement(
                                    Element(
                                        id = activity.viewModel.item.id,
                                        groupId = activity.idGroup.toInt(),
                                        value = inputValue.value,
                                        image = inputValueLinkImage.value,
                                        link = inputValueLink.value
                                    )
                                )
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(60.dp)
                                .padding(10.dp, 10.dp, 0.dp, 10.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.accept),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}