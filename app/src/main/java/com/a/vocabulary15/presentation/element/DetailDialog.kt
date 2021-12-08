package com.a.vocabulary15.presentation.element

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.a.vocabulary15.R
import com.a.vocabulary15.presentation.ElementsActivity

@Composable
fun DetailDialog(activity: ElementsActivity) {
    if (activity.viewModel.isDetailElementOpen.value) {
        Dialog(onDismissRequest = { activity.viewModel.isDetailElementOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .height(490.dp)
                    .fillMaxWidth()
                    .padding(5.dp),
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    AndroidView(modifier = Modifier.height(400.dp),
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
                    Row {
                        IconButton(
                            onClick = {
                                activity.viewModel.isDetailElementOpen.value = false
                                activity.viewModel.isDeleteElementOpen.value = true
                            },//activity.viewModel.deleteElement(element.id) },
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
                                activity.viewModel.isDetailElementOpen.value = false
                                activity.viewModel.isEditElementOpen.value = true
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
                        AndroidView(modifier = Modifier.height(50.dp),
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
                                if (activity.viewModel.item.image != null) {
                                    if (activity.viewModel.item.image!!.contains("https://") || activity.viewModel.item.image!!.contains(
                                            "http://"
                                        )
                                    ) {
                                        it.loadUrl(activity.viewModel.item.image!!)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}