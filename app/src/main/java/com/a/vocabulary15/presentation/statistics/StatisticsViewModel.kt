package com.a.vocabulary15.presentation.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.Statistics
import com.a.vocabulary15.domain.usecases.GetGroups
import com.a.vocabulary15.domain.usecases.GetStatistics
import com.a.vocabulary15.presentation.common.ViewState
import com.a.vocabulary15.presentation.statistics.data.StatisticsEntity
import com.a.vocabulary15.presentation.util.convertMillisecondsToCalendar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    var getStatistics: GetStatistics,
    var getGroups: GetGroups
) : ViewModel() {

    private val viewStateMutable: MutableStateFlow<ViewState<*>> =
        MutableStateFlow(ViewState.Loading)
    val viewState = viewStateMutable.asStateFlow()
    private var getStatisticsJob: Job? = null
    private var getStatisticsMonthJob: Job? = null

    companion object {
        const val MONTH_MILLISECONDS = 2628002880
    }

    //events
    fun onEvent(event: StatisticsEvent) {
        when (event) {
            is StatisticsEvent.FetchStatistics -> {
                getStatisticsEntity()
            }
            is StatisticsEvent.FetchStatisticsByMonth -> {
                getStatisticsByMonth()
            }
        }
    }


    fun getStatisticFlow(
        getStatistics: Flow<List<Statistics>>,
        calendar: Calendar
    ): Flow<List<Statistics>> {
        return getStatistics.onEach { statistics ->

            //val listMonthTitle = mutableMapOf<String, Int>()
            val listInt = mutableListOf<Int>()
            for (i in 0..11) {
                val currentTime = calendar.timeInMillis - MONTH_MILLISECONDS * i
                val pastTime = calendar.timeInMillis - MONTH_MILLISECONDS * (i + 1)
                calendar.timeInMillis = pastTime
                if (calendar.get(Calendar.MONTH) < 10) {
                    //listMonthTitle["0${calendar.get(Calendar.MONTH) + 1}\n${calendar.get(Calendar.YEAR)}"] =
                    listInt.add(findOutMonth(currentTime, pastTime, statistics))
                } else {
                    //listMonthTitle["${calendar.get(Calendar.MONTH) + 1}\n${calendar.get(Calendar.YEAR)}"] =
                    listInt.add(findOutMonth(currentTime, pastTime, statistics))
                }
            }
            notifyPostStateCountList(listInt)
        }
    }
    /*private fun getStatisticsByMonth() = viewModelScope.launch(Dispatchers.IO) {
        getStatisticsMonthJob?.cancel()
        getStatisticsMonthJob = viewModelScope.launch {getStatisticFlow(getStatistics.invoke(),
            Calendar.getInstance()) }
    }*/

    private fun getStatisticsByMonth() = viewModelScope.launch(Dispatchers.IO) {
        getStatisticsMonthJob?.cancel()
        getStatisticsMonthJob =getStatistics.invoke().onEach { statistics ->
            val calendar = Calendar.getInstance()
            //val listMonthTitle = mutableMapOf<String, Int>()
            val listInt = mutableListOf<Int>()
            var currentTime = calendar.timeInMillis
            var pastTime = calendar.timeInMillis - MONTH_MILLISECONDS
            for (i in 0..11) {
                if (calendar.get(Calendar.MONTH) < 10) {
                    //listMonthTitle["0${calendar.get(Calendar.MONTH) + 1}\n${calendar.get(Calendar.YEAR)}"] =
                    listInt.add(findOutMonth(currentTime, pastTime, statistics))
                } else {
                    //listMonthTitle["${calendar.get(Calendar.MONTH) + 1}\n${calendar.get(Calendar.YEAR)}"] =
                    listInt.add(findOutMonth(currentTime, pastTime, statistics))
                }
                currentTime = pastTime
                pastTime = currentTime - MONTH_MILLISECONDS
            }
            notifyPostStateCountList(listInt)
        }.launchIn(viewModelScope)
    }

    fun findOutMonth(currentTime: Long, pastTime: Long, list: List<Statistics>):Int {
        var count = 0
        for (statistic in list) {
            if (statistic.date in (pastTime + 1) until currentTime) {
                count += statistic.points
            }
        }
        return count
    }

    fun getStatisticsEntityTest(
        getGroups: Flow<List<Group>>,
        getStatistics: Flow<List<Statistics>>
    ): Flow<List<StatisticsEntity>> {
        return getGroups.zip(getStatistics) { groups, statistics ->
            val listStatisticsEntity = mutableListOf<StatisticsEntity>()
            statistics.onEach {
                groups.onEach { group ->
                    if (group.id == it.groupId) {
                        listStatisticsEntity.add(
                            StatisticsEntity(
                                date = convertMillisecondsToCalendar(
                                    Calendar.getInstance(),
                                    it.date
                                ),
                                points = it.points,
                                groupTitle = group.name
                            )
                        )
                    }
                }
            }
            return@zip listStatisticsEntity
        }
    }

    fun getStatisticsEntity() = viewModelScope.launch(Dispatchers.IO) {
        getStatisticsJob?.cancel()
        getStatisticsJob = viewModelScope.launch {
            getStatisticsEntityTest(
                getGroups.invoke(),
                getStatistics.invoke()
            ).collect { notifyPostState(it) }
        }
    }
    private fun notifyPostStateCountList(list: List<Int>) {
        viewStateMutable.value = ViewState.Success(list)
    }

    private fun notifyPostState(list: List<StatisticsEntity>) {
        viewStateMutable.value = ViewState.Success(list)
    }
}