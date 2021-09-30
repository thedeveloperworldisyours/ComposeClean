package com.a.vocabulary15.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.a.vocabulary15.presentation.ui.ElementScreen
import com.a.vocabulary15.presentation.ui.theme.Vocabulary15Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ElementsActivity : AppCompatActivity() {

    var idGroup = ""

    @Inject
    lateinit var elementsViewModel: ElementsViewModel
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras != null) {
            idGroup = extras.getInt("idGroup").toString()
        }
        setContent {
            Vocabulary15Theme {
                Surface(color = MaterialTheme.colors.background) {
                    ElementScreen(
                        liveData = elementsViewModel.genericLiveData,
                        idGroup,
                        elementsViewModel
                    ) {
                        elementsViewModel.deleteGroupWithElements(idGroup.toInt())
                        this.finish()
                    }
                }
            }
        }
    }
}