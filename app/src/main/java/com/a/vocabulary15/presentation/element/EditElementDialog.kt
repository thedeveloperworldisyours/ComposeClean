package com.a.vocabulary15.presentation.element


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.MainActivity
import java.util.*

@Composable
fun EditElementDialog(
    activity: MainActivity,
    viewModel: ElementsViewModel) {
    if (viewModel.isEditElementOpen) {
        Dialog(onDismissRequest = { viewModel.isDeleteElementOpen = false }) {
            val link = stringResource(id = R.string.word_reference)
            val linkImage = stringResource(id = R.string.google_reference)
            val inputValue = rememberSaveable { mutableStateOf(viewModel.item.value) }
            val inputValueLink = rememberSaveable { mutableStateOf(viewModel.item.link) }
            val inputValueLinkImage = rememberSaveable { mutableStateOf(viewModel.item.image) }
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
                    TextField(
                        value = inputValue.value,
                        maxLines = 1,
                        onValueChange = { inputValue.value = it },
                        placeholder = { Text(text = stringResource(id = R.string.enter_name)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                ContextCompat.startActivity(
                                    activity,
                                    Intent(Intent.ACTION_VIEW, Uri.parse(link)),
                                    null
                                )
                            },
                            modifier = Modifier
                                .size(56.dp)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_baseline_add_link_24),
                                stringResource(id = R.string.enter_link)
                            )
                        }
                        TextField(
                            value = inputValueLink.value,
                            maxLines = 1,
                            onValueChange = { inputValueLink.value = it },
                            placeholder = { Text(text = stringResource(id = R.string.enter_link)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 0.dp, 10.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                ContextCompat.startActivity(
                                    activity,
                                    Intent(Intent.ACTION_VIEW, Uri.parse(linkImage)),
                                    null
                                )
                            },
                            modifier = Modifier
                                .size(56.dp)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_baseline_add_link_24),
                                stringResource(id = R.string.enter_image_link)
                            )
                        }
                        TextField(
                            value = inputValueLinkImage.value,
                            maxLines = 1,
                            onValueChange = { inputValueLinkImage.value = it },
                            placeholder = { Text(text = stringResource(id = R.string.enter_image_link)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 0.dp, 10.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {

                        Button(
                            onClick = {
                                viewModel.isEditElementOpen = false
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(40.dp)
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
                                viewModel.isEditElementOpen = false
                                viewModel.editElement(
                                    Element(
                                        id = viewModel.item.id,
                                        groupId = viewModel.idGroup.toInt(),
                                        value = inputValue.value.lowercase(Locale.getDefault()),
                                        image = inputValueLinkImage.value,
                                        link = inputValueLink.value
                                    )
                                )
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(40.dp),
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