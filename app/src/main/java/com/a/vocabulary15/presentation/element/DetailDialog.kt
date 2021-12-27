package com.a.vocabulary15.presentation.element

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

@Composable
fun DetailDialog(activity: ElementsActivity) {
    if (activity.viewModel.isDetailElementOpen) {
        Dialog(onDismissRequest = { activity.viewModel.isDetailElementOpen = false }) {
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
                            if (activity.viewModel.item.link.contains("https://") || activity.viewModel.item.link.contains(
                                    "http://"
                                )
                            ) {
                                it.loadUrl(activity.viewModel.item.link)
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
                                activity.viewModel.isDetailElementOpen = false
                                activity.viewModel.isDeleteElementOpen = true
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
                                activity.viewModel.isDetailElementOpen = false
                                activity.viewModel.isEditElementOpen = true
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
                            painter = rememberImagePainter(activity.viewModel.item.image),
                            contentDescription = null
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(13.dp)
                        )
                        Button(
                            onClick = {
                                activity.viewModel.isDetailElementOpen = false
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