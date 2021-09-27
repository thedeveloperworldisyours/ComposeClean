package com.a.vocabulary15.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.presentation.ui.GetGroupScreen
import com.a.vocabulary15.presentation.ui.composables.AddGroupButton
import com.a.vocabulary15.presentation.ui.composables.AddGroupTextField
import com.a.vocabulary15.presentation.ui.theme.Vocabulary15Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    /*private var responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            mainViewModel.getGroup()
        }
        //responseLauncher.launch(Intent(this, AddGroupActivity::class.java))
                    //}, {
        */


    @Inject
    lateinit var mainViewModel: MainViewModel
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vocabulary15Theme {
                Surface(color = MaterialTheme.colors.background) {
                    mainViewModel.getGroup()
                    GetGroupScreen(liveData = mainViewModel.getGroupLiveData, mainViewModel){
                        val intent = Intent(this, GetElementsActivity::class.java)
                        intent.putExtra("idGroup", it)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

