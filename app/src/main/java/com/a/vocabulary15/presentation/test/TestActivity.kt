package com.a.vocabulary15.presentation.test

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.a.vocabulary15.presentation.test.composables.TestScreen
import com.a.vocabulary15.presentation.ui.theme.Vocabulary15Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : AppCompatActivity() {

    var idGroup = ""
    var elementName = ""
    var mode = 1

    val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras != null) {
            idGroup = extras.getString("idGroup") ?: ""
            elementName = extras.getString("elementName") ?: ""
            mode = extras.getInt("mode")
            Log.e("Javier", "- - >$mode")
            viewModel.getElements(idGroup.toInt())
        }
        setContent {
            Vocabulary15Theme {
                Surface(color = MaterialTheme.colors.background) {
                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()
                    Scaffold(
                        scaffoldState = scaffoldState,
                        modifier = Modifier.fillMaxWidth(),
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = elementName)
                                }
                            )
                        }) {
                        TestScreen(activity = this, scope, scaffoldState)
                    }
                }
            }
        }
    }
}