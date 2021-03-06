package com.a.vocabulary15.presentation.element.composables

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.element.ElementEvent
import com.a.vocabulary15.presentation.element.ElementsViewModel
import com.a.vocabulary15.util.TestTags
import java.util.*

@Composable
fun AddElementDialog(
    context: Context,
    viewModel: ElementsViewModel
) {
    if (viewModel.state.value.isAddElementOpen) {
        Dialog(onDismissRequest = { viewModel.onEvent(ElementEvent.OpenAddElementDialog(false)) }) {
            val link = stringResource(id = R.string.word_reference)
            val linkImage = stringResource(id = R.string.google_reference)
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .padding(5.dp)
                    .testTag(TestTags.NEW_ELEMENT_DIALOG),
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
                    TextField(
                        value = viewModel.state.value.inputValue,
                        maxLines = 1,
                        onValueChange = { viewModel.onEvent(ElementEvent.SetInputValue(it)) },
                        placeholder = { Text(text = stringResource(id = R.string.enter_name)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                            .testTag(TestTags.NEW_NAME_ELEMENT_TEXT_FIELD)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                ContextCompat.startActivity(
                                    context,
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
                            value = viewModel.state.value.inputValueLink,
                            maxLines = 1,
                            onValueChange = {
                                viewModel.onEvent(ElementEvent.SetInputValueLink(it))
                                if (it.isNotEmpty()) {
                                    viewModel.onEvent(
                                        ElementEvent.SetErrorLinkWord(
                                            !URLUtil.isValidUrl(
                                                it
                                            )
                                        )
                                    )
                                } else {
                                    viewModel.onEvent(ElementEvent.SetErrorLinkWord(false))
                                }
                            },
                            trailingIcon = {
                                if (viewModel.state.value.isErrorLinkWord)
                                    Icon(
                                        Icons.Filled.Error,
                                        "error",
                                        tint = MaterialTheme.colors.error
                                    )
                            },
                            isError = viewModel.state.value.isErrorLinkWord,
                            placeholder = { Text(text = stringResource(id = R.string.enter_link)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 0.dp, 10.dp)
                                .testTag(TestTags.NEW_LINK_ELEMENT_TEXT_FIELD)
                        )
                    }
                    if (viewModel.state.value.isErrorLinkWord) {
                        Text(
                            text = stringResource(id = R.string.text_error_word),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(56.dp, 0.dp, 0.dp, 16.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                ContextCompat.startActivity(
                                    context,
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
                            value = viewModel.state.value.inputValueLinkImage,
                            maxLines = 1,
                            onValueChange = {
                                viewModel.onEvent(ElementEvent.SetInputValueLinkImage(it))
                                if (it.isNotEmpty()) {
                                    viewModel.onEvent(
                                        ElementEvent.SetErrorLinkImage(
                                            !URLUtil.isValidUrl(
                                                it
                                            )
                                        )
                                    )
                                } else {
                                    viewModel.onEvent(ElementEvent.SetErrorLinkImage(false))
                                }
                            },
                            trailingIcon = {
                                if (viewModel.state.value.isErrorLinkImage)
                                    Icon(
                                        Icons.Filled.Error,
                                        "error",
                                        tint = MaterialTheme.colors.error
                                    )
                            },
                            placeholder = { Text(text = stringResource(id = R.string.enter_image_link)) },
                            isError = viewModel.state.value.isErrorLinkImage,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 0.dp, 10.dp)
                                .testTag(TestTags.NEW_IMAGE_ELEMENT_TEXT_FIELD)
                        )
                    }
                    if (viewModel.state.value.isErrorLinkImage) {
                        Text(
                            text = stringResource(id = R.string.text_error_image),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(56.dp, 0.dp, 0.dp, 16.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                viewModel.onEvent(ElementEvent.OpenAddElementDialog(false))
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
                                viewModel.onEvent(ElementEvent.OpenAddElementDialog(false))
                                viewModel.setElement(
                                    Element(
                                        id = 0,
                                        groupId = viewModel.state.value.idGroup,
                                        value = viewModel.state.value.inputValue.lowercase(Locale.getDefault()),
                                        image = viewModel.state.value.inputValueLinkImage,
                                        link = viewModel.state.value.inputValueLink
                                    )
                                )
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(60.dp)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                .testTag(TestTags.SAVE_ELEMENT),
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