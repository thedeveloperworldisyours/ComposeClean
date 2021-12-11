package com.a.vocabulary15.presentation.element

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.a.vocabulary15.presentation.test.TestActivity
import com.a.vocabulary15.presentation.ui.theme.Vocabulary15Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ElementsActivity : AppCompatActivity() {

    var idGroup = ""
    var elementName = ""

    @Inject
    lateinit var viewModel: ElementsViewModel

    private var responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras != null) {
            idGroup = extras.getInt("idGroup").toString()
            elementName = extras.getString("elementName") ?: ""
            viewModel.getElements(idGroup.toInt())
        }
        setContent {
            Vocabulary15Theme {
                Surface(color = MaterialTheme.colors.background) {
                    ElementScreen(this) {
                        val intent = Intent(this, TestActivity::class.java)
                        intent.putExtra("idGroup", idGroup)
                        intent.putExtra("elementName", elementName)
                        responseLauncher.launch(intent)
                    }
                }
            }
        }
    }
}