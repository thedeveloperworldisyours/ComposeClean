package com.a.vocabulary15

import com.a.vocabulary15.data.repository.FakeRepository
import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.usecases.*
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetGroupTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var setGroup: SetGroup

    private lateinit var fakeRepository: FakeRepository

    private lateinit var setFakeGroup: SetGroup

    private lateinit var getFakeGroup: GetGroups

    private var group = Group(1, "name")

    @Before
    fun setup(){
        setGroup = SetGroupImpl(repository)
        fakeRepository = FakeRepository()
        getFakeGroup = GetGroupsImpl(fakeRepository)
        setFakeGroup = SetGroupImpl(fakeRepository)
    }

    @Test
    fun `set group with success`() {
        runBlocking {
            setGroup.invoke(group)

            verify(repository).setGroup(group)
        }
    }

    @Test
    fun `set group with successfully with fake repository`() = runBlocking {

        val oldElements = getFakeGroup.invoke()
        val newElements = setFakeGroup.invoke(group)

        assertThat(newElements).isNotEqualTo(oldElements)
    }
}