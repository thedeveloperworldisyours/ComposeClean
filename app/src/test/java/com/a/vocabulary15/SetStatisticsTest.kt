package com.a.vocabulary15

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Statistics
import com.a.vocabulary15.domain.usecases.SetStatistics
import com.a.vocabulary15.domain.usecases.SetStatisticsImpl
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetStatisticsTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var setStatistics: SetStatistics

    private val statistics = Statistics(
        0,
     0,
     1,
     1)

    @Before
    fun setup() {
        setStatistics = SetStatisticsImpl(repository)
    }


    @Test
    fun `set statistics with success`() {
        runBlocking {
            setStatistics.invoke(statistics)

            verify(repository).setStatistics(statistics)
        }
    }
}