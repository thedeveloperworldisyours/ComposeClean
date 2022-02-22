package com.a.vocabulary15.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Statistics
import com.a.vocabulary15.domain.usecases.GetElements
import com.a.vocabulary15.domain.usecases.SetStatistics
import com.a.vocabulary15.presentation.test.TestViewModel
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TestViewModelTest {

    @Mock
    private lateinit var getElements: GetElements

    @Mock
    private lateinit var setStatistics: SetStatistics

    lateinit var testViewModel: TestViewModel

    private val idGroup = 0

    private val statistics = Statistics(
        0,
        0,
        1,
        1
    )

    @Before
    fun setup() {
        val savedStateHandle = SavedStateHandle().apply {
            set("idGroup", idGroup)
            set("elementName", "elementName")
        }
        testViewModel = TestViewModel(getElements, setStatistics, savedStateHandle)
    }

    @Test
    fun `get element success`() {
        runBlocking {
            testViewModel.getElements(idGroup)

            verify(getElements).invoke(idGroup)
        }
    }

    @Test
    fun `insert statistics successfully`() {
        runBlocking {
            testViewModel.insertStatistics(statistics)

            verify(setStatistics).invoke(statistics)
        }
    }

    @Test
    fun `generating underscores with success`() {
        val hello = "hello"
        val underscores = mutableListOf<String>()
        underscores.add("_")
        underscores.add("_")

        underscores.add("_")
        underscores.add("_")

        underscores.add("_")

        val resource = testViewModel.generateUnderscores(hello)

        Truth.assertThat(resource).isEqualTo(underscores)
    }

    @Test
    fun `setting Hangman Image with success`() {

        val resource = testViewModel.setHangmanImage(2)

        Truth.assertThat(resource).isEqualTo(R.drawable.ic_hangman_4)
    }
}