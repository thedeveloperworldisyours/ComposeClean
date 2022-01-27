package com.a.vocabulary15.presentation.test

import com.a.vocabulary15.domain.model.Element

data class TestState(
    val elements: List<Element> = emptyList(),
    val levelMode:Int = -1,
    val text:String = "",
    val right:Int = 0,
    val wrong:Int = 0,
    val randomNumber:Int = -1,
    val isTestFinishOpen: Boolean = false,
    val isChooseLevelOpen: Boolean = true)

