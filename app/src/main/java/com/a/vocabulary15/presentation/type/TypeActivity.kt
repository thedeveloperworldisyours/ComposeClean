package com.a.vocabulary15.presentation.type

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import com.a.vocabulary15.presentation.ui.theme.Vocabulary15Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypeActivity  : ComponentActivity() {

    val viewModel: TypeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vocabulary15Theme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(scaffoldState =scaffoldState) {
                        TypeScreen(viewModel, scope, scaffoldState)
                    }
                }
            }
        }
    }
}