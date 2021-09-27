package com.a.vocabulary15.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.presentation.ui.GetGroupScreen
import com.a.vocabulary15.presentation.ui.theme.Vocabulary15Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    /*private var responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            mainViewModel.getGroup()
        }*/

    private var nameGroup = ""

    @Inject
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vocabulary15Theme {
                Surface(color = MaterialTheme.colors.background) {
                    mainViewModel.getGroup()
                    nameGroup = GetGroupScreen(liveData = mainViewModel.getGroupLiveData, {
                        mainViewModel.insertAndGetGroup(Group(0, nameGroup))
                        //responseLauncher.launch(Intent(this, AddGroupActivity::class.java))
                    }, {
                        val intent = Intent(this, GetElementsActivity::class.java)
                        intent.putExtra("idGroup", it)
                        startActivity(intent)
                    })
                }
            }
        }
    }
}

