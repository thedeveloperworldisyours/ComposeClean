package com.a.vocabulary15.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.a.vocabulary15.presentation.element.ElementsActivity
import com.a.vocabulary15.presentation.group.GroupScreen
import com.a.vocabulary15.presentation.group.MainViewModel
import com.a.vocabulary15.presentation.ui.theme.Vocabulary15Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.getGroup()
        }

    val viewModel: MainViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vocabulary15Theme {
                Surface(color = MaterialTheme.colors.background) {

                    GroupScreen(liveData = viewModel.getGroupLiveData, this){
                        idGroup, elementName ->
                        val intent = Intent(this, ElementsActivity::class.java)
                        intent.putExtra("idGroup", idGroup)
                        intent.putExtra("elementName", elementName)
                        intent.putExtra("mode", 1)
                        responseLauncher.launch(intent)
                    }
                }
            }
        }
    }
}

