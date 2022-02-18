package com.a.vocabulary15

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.usecases.GetStatistics
import com.a.vocabulary15.domain.usecases.GetStatisticsImpl
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetStatisticsTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var getStatistics: GetStatistics

    @Before
    fun setup() {
        getStatistics = GetStatisticsImpl(repository)
    }

    @Test
    fun `get statistics successfully`() {
        getStatistics.invoke()

        verify(repository).getStatistics()
    }
}