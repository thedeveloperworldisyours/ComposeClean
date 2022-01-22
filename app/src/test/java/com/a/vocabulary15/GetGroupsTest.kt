package com.a.vocabulary15

import com.a.vocabulary15.data.repository.FakeRepository
import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.usecases.GetGroups
import com.a.vocabulary15.domain.usecases.GetGroupsImpl
import com.a.vocabulary15.domain.usecases.SetGroup
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetGroupsTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var getGroups: GetGroups

    private lateinit var fakeRepository: FakeRepository

    private lateinit var getFakeGroup: GetGroups

    @Before
    fun setup(){
        getGroups = GetGroupsImpl(repository)
        fakeRepository = FakeRepository()
        getFakeGroup = GetGroupsImpl(fakeRepository)
        val group = Group(1, "name")
        runBlocking {
            fakeRepository.setGroup(group)
        }
    }

    @Test
    fun `get groups successfully`(){
        runBlocking {
            getGroups.invoke()

            verify(repository).getGroups()
        }
    }

    @Test
    fun `get group successfully with fakerepository` () {
        runBlocking {
            val groups = getFakeGroup().first()

            Truth.assertThat(groups).isNotEmpty()
        }

    }
}