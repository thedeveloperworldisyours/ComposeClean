package com.a.vocabulary15.presentation.element.composables

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.a.vocabulary15.R
import com.a.vocabulary15.presentation.element.ElementEvent
import com.a.vocabulary15.presentation.element.ElementsViewModel

@Composable
fun DetailDialog(viewModel: ElementsViewModel) {
    if (viewModel.state.value.isDetailElementOpen) {
        Dialog(onDismissRequest = { viewModel.state.value.isDetailElementOpen = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ) {
                Box {
                    AndroidView(
                        factory = {
                            WebView(it).apply {
                                webViewClient = object : WebViewClient() {
                                    override fun shouldOverrideUrlLoading(
                                        view: WebView?,
                                        request: WebResourceRequest?
                                    ): Boolean {
                                        return false
                                    }
                                }
                            }
                        }, update = {
                            if (viewModel.item.link.contains("https://") || viewModel.item.link.contains(
                                    "http://"
                                )
                            ) {
                                it.loadUrl(viewModel.item.link)
                            }
                        }
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(Color.White)
                            .padding(5.dp)
                    ) {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(ElementEvent.OpenDetailElementDialog(false))
                                viewModel.onEvent(ElementEvent.OpenDeleteElementDialog(true))
                            },
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.Red)
                        ) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.delete),
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        IconButton(
                            onClick = {
                                viewModel.onEvent(ElementEvent.SetInputValue(viewModel.item.value))
                                viewModel.onEvent(ElementEvent.SetInputValueLink(viewModel.item.link))
                                viewModel.onEvent(ElementEvent.SetInputValueLinkImage(viewModel.item.image))
                                viewModel.onEvent(ElementEvent.OpenDetailElementDialog(false))
                                viewModel.onEvent(ElementEvent.OpenEditElementDialog(true))
                            },
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.LightGray)
                        ) {
                            Icon(
                                Icons.Filled.Edit,
                                contentDescription = stringResource(R.string.edit),
                                tint = Color.White
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .padding(13.dp)
                        )
                        Image(
                            modifier = Modifier
                                .size(50.dp),
                            painter = rememberImagePainter(viewModel.item.image),
                            contentDescription = null
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(13.dp)
                        )
                        Button(
                            onClick = {
                                viewModel.onEvent(ElementEvent.OpenDetailElementDialog(false))
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(50.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.close),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}