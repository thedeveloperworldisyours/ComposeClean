package com.a.vocabulary15.viewmodel

import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.Statistics
import com.a.vocabulary15.domain.usecases.GetGroups
import com.a.vocabulary15.domain.usecases.GetStatistics
import com.a.vocabulary15.presentation.common.ViewState
import com.a.vocabulary15.presentation.statistics.StatisticsViewModel
import com.a.vocabulary15.presentation.statistics.data.StatisticsEntity
import com.a.vocabulary15.presentation.util.convertMillisecondsToCalendar
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
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

    lateinit var statisticsViewModel: StatisticsViewModel

    @Before
    fun setup(){
        runBlocking {
            statisticsViewModel = StatisticsViewModel(getStatistics, getGroups)
        }
    }

    @Test
    fun `get Statistics list successfully`() {
        runBlocking {

            val group = Group(1, "name")
            val secondGroup = Group(2, "Second Name")
            val groups = mutableListOf<Group>()
            groups.add(group)
            groups.add(secondGroup)

            val firstStatistics = Statistics(
                id=1,
                date = 1110L,
                points = 1,
                groupId = 1)
            val secondStatistics = Statistics(
                id=1,
                date = 1110L,
                points = 2,
                groupId = 2)
            val statisticsList = mutableListOf<Statistics>()
            statisticsList.add(firstStatistics)
            statisticsList.add(secondStatistics)

            val listStatisticsEntity = mutableListOf<StatisticsEntity>()
            listStatisticsEntity.add(
                StatisticsEntity(
                    convertMillisecondsToCalendar(
                    Calendar.getInstance(),
                        1110L
                ),
                    points = 1,
                    groupTitle = "name")
            )
            listStatisticsEntity.add(
                StatisticsEntity(
                    convertMillisecondsToCalendar(
                        Calendar.getInstance(),
                        1110L
                    ),
                    points = 2,
                    groupTitle = "Second Name")
            )

            `when`(getGroups.invoke()).thenReturn(flow { emit(groups) })
            `when`(getStatistics.invoke()).thenReturn(flow { emit(statisticsList) })

            val result = statisticsViewModel.getStatisticsEntity()

            verify(getGroups).invoke()
            verify(getStatistics).invoke()
            Assert.assertEquals(statisticsViewModel.viewState.value, result)
        }
    }
}