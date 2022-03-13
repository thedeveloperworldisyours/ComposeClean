package com.a.vocabulary15.viewmodel

import app.cash.turbine.test
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.Statistics
import com.a.vocabulary15.domain.usecases.GetGroups
import com.a.vocabulary15.domain.usecases.GetStatistics
import com.a.vocabulary15.presentation.common.ViewState
import com.a.vocabulary15.presentation.statistics.StatisticsViewModel
import com.a.vocabulary15.presentation.statistics.data.StatisticsEntity
import com.a.vocabulary15.presentation.util.convertMillisecondsToCalendar
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class StatisticsViewModelTest {

    @Mock
    private lateinit var getStatistics: GetStatistics

    @Mock
    private lateinit var getGroups: GetGroups

    private lateinit var statisticsViewModel: StatisticsViewModel
    val group = Group(1, "name")
    private val secondGroup = Group(2, "Second Name")
    private val groups = mutableListOf<Group>()
    private val firstStatistics = Statistics(
        id = 1,
        date = 1110L,
        points = 1,
        groupId = 1
    )
    private val secondStatistics = Statistics(
        id = 1,
        date = 1110L,
        points = 2,
        groupId = 2
    )
    private val statisticsList = mutableListOf<Statistics>()
    private val listStatisticsEntity = mutableListOf<StatisticsEntity>()
    @Before
    fun setup() {
        runBlocking {
            statisticsViewModel = StatisticsViewModel(getStatistics, getGroups)
        }

        groups.add(group)
        groups.add(secondGroup)
        statisticsList.add(firstStatistics)
        statisticsList.add(secondStatistics)
        listStatisticsEntity.add(
            StatisticsEntity(
                convertMillisecondsToCalendar(
                    Calendar.getInstance(),
                    1110L
                ),
                points = 1,
                groupTitle = "name"
            )
        )
        listStatisticsEntity.add(
            StatisticsEntity(
                convertMillisecondsToCalendar(
                    Calendar.getInstance(),
                    1110L
                ),
                points = 2,
                groupTitle = "Second Name"
            )
        )
    }

    @Test
    fun `get Statistics list loading`() {
        runBlocking {

            val job = launch {
                statisticsViewModel.viewState.test {
                    val emission = awaitItem()
                    assertThat(emission).isEqualTo(ViewState.Loading)
                    cancelAndConsumeRemainingEvents()
                }
            }

            statisticsViewModel.getStatisticsEntity()
            job.join()
            job.cancel()
        }
    }

    @Test
    fun `get Statistics list successfully`() {
        runBlocking {
            `when`(getGroups.invoke()).thenReturn(flow { emit(groups) })
            `when`(getStatistics.invoke()).thenReturn(flow { emit(statisticsList) })

            statisticsViewModel.getStatisticsEntityTest(getGroups.invoke(), getStatistics.invoke()).test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(listStatisticsEntity)
                cancelAndConsumeRemainingEvents()
            }

            verify(getGroups).invoke()
            verify(getStatistics).invoke()
        }
    }

    @Test
    fun `get Statistics list successfully without mocks`() {
        runBlocking {
            statisticsViewModel.getStatisticsEntityTest(flow { emit(groups) }, flow { emit(statisticsList) }).test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(listStatisticsEntity)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}