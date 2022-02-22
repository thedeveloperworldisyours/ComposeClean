package com.a.vocabulary15.viewmodel

import androidx.compose.ui.graphics.Color
import com.a.vocabulary15.presentation.util.findFinalScoreColor
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class UtilsTest {


    @Before
    fun setup() {}

    @Test
    fun `finding green color in final score color when score is positive`() {
        val color = findFinalScoreColor(1)

        Truth.assertThat(color).isEqualTo(Color(0xFF51983C))
    }

    @Test
    fun `finding gray color in final score color when score is neutral`() {
        val color = findFinalScoreColor(0)

        Truth.assertThat(color).isEqualTo(Color.Gray)
    }

    @Test
    fun `finding green color in final score color when score is negative`() {
        val color = findFinalScoreColor(-1)

        Truth.assertThat(color).isEqualTo(Color.Red)
    }
}