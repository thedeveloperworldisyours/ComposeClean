package com.a.vocabulary15.viewmodel

import com.a.vocabulary15.domain.usecases.GetElements
import com.a.vocabulary15.presentation.test.TestViewModel
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

    val idGroup = 0

    @Before
    fun setup() {
        testViewModel = TestViewModel(getElements)
    }

    @Test
    fun `get element success`(){
        runBlocking {
            testViewModel.getElements(idGroup)

            verify(getElements).invoke(idGroup)
        }
    }
}