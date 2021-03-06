package com.a.vocabulary15.presentation.test

import androidx.compose.ui.graphics.Color
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element

data class TestState(
    val idGroup: Int = -1,
    val elementName: String = "",
    val elements: List<Element> = emptyList(),
    val levelMode: Int = -1,
    val text: String = "",
    val right: Int = 0,
    val wrong: Int = 0,
    val finalScoreColor: Color = Color.Black,
    val randomNumber: Int = -1,
    val isTestFinishOpen: Boolean = false,
    val isChooseLevelOpen: Boolean = true,
    val asked: List<Boolean> = emptyList(),
    val wordShowed: List<String> = emptyList(),
    val wrongLetters: List<String> = emptyList(),
    val hangmanStep: Int = R.drawable.ic_hangman_start
)

