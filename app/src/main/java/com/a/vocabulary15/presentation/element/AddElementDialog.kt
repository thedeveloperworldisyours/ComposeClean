package com.a.vocabulary15.presentation.element

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.ui.composables.AddGroupTextField
import java.util.*

@Composable
fun AddElementDialog(activity: ElementsActivity) {
    if (activity.viewModel.isAddElementOpen.value) {
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
                        text = stringResource(R.string.add_element),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    val link = stringResource(id = R.string.word_reference)
                    val linkImage = stringResource(id = R.string.google_reference)
                    val returnName = AddGroupTextField(stringResource(id = R.string.enter_name))
                    val returnLink = AddGroupTextField(stringResource(id = R.string.enter_link))
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
                    val returnImageLink = AddGroupTextField(stringResource(id = R.string.enter_image_link))
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
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
                                .width(60.dp)
                                .height(60.dp)
                                .padding(10.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.search_image),
                                color = Color.White
                            )
                        }
                        Button(
                            onClick = {
                                activity.viewModel.isAddElementOpen.value = false
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
                                activity.viewModel.isAddElementOpen.value = false
                                activity.viewModel.setElement(
                                    Element(
                                        id = 0,
                                        groupId = activity.idGroup.toInt(),
                                        value = returnName.lowercase(Locale.getDefault()),
                                        image = returnImageLink,
                                        link = returnLink
                                    )
                                )
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(60.dp)
                                .padding(0.dp, 10.dp, 0.dp, 10.dp),
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