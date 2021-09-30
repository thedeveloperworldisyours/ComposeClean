package com.a.vocabulary15.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.a.vocabulary15.presentation.ui.GroupScreen
import com.a.vocabulary15.presentation.ui.theme.Vocabulary15Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            mainViewModel.getGroup()
        }

    @Inject
    lateinit var mainViewModel: MainViewModel

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vocabulary15Theme {
                Surface(color = MaterialTheme.colors.background) {

                    GroupScreen(liveData = mainViewModel.getGroupLiveData, mainViewModel){
                        val intent = Intent(this, ElementsActivity::class.java)
                        intent.putExtra("idGroup", it)
                        responseLauncher.launch(intent)
                    }
                }
            }
        }
    }
}

