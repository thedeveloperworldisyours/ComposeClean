package com.a.vocabulary15

import com.a.vocabulary15.data.repository.FakeRepository
import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.usecases.GetElements
import com.a.vocabulary15.domain.usecases.GetElementsImpl
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetElementsTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var fakeRepository: FakeRepository

    private lateinit var getElements: GetElements
    private lateinit var getFakeElements : GetElements

    private var idGroup = 2
    @Before
    fun setup(){
        fakeRepository = FakeRepository()
        getFakeElements = GetElementsImpl(fakeRepository)
        getElements = GetElementsImpl(repository)
    }

    @Test
    fun `get element with success`(){
        runBlocking {
            getElements.invoke(idGroup)

            verify(repository).getElements(idGroup)
        }
    }

    /*@Test
    fun `get element`() = runBlocking {
        val elements = getFakeElements.invoke(1)

        assert(elements)
    }*/
}