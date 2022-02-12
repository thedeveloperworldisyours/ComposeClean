package com.a.vocabulary15.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.usecases.GetElements
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

    lateinit var testViewModel: TestViewModel

    private val idGroup = 0

    @Before
    fun setup() {
        val savedStateHandle = SavedStateHandle().apply {
            set("idGroup", idGroup)
            set("elementName", "elementName")
        }
        testViewModel = TestViewModel(getElements, savedStateHandle)
    }

    @Test
    fun `get element success`(){
        runBlocking {
            testViewModel.getElements(idGroup)

            verify(getElements).invoke(idGroup)
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
    fun `setting Hangman Image with success`(){

        val resource = testViewModel.setHangmanImage(2)

        Truth.assertThat(resource).isEqualTo(R.drawable.ic_hangman_4)
    }
}