package com.a.vocabulary15.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.presentation.ui.AddGroupScreen
import com.a.vocabulary15.presentation.ui.theme.Vocabulary15Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddGroupActivity : AppCompatActivity() {

    private var nameGroup = ""
    @Inject
    lateinit var viewModel: AddGroupViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vocabulary15Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    nameGroup = AddGroupScreen(liveData = viewModel.setGroupLiveData, this) {
                        viewModel.insertAndGetGroup(Group(0, nameGroup))
                    }
                }
            }
        }
    }
}