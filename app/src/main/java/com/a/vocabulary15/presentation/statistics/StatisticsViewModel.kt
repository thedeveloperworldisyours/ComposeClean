package com.a.vocabulary15.presentation.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    //events
    fun onEvent(event: StatisticsEvent) {
        when (event) {
            is StatisticsEvent.FetchStatistics -> {
                getStatisticsEntity()
            }
        }
    }

    fun getStatisticsEntity() = viewModelScope.launch(Dispatchers.IO) {
        getStatisticsJob?.cancel()
        getStatisticsJob = viewModelScope.launch {
            getGroups.invoke().zip(getStatistics.invoke()) { groups, statistics ->
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
            }.collect { notifyPostState(it) }
        }
    }

    private fun notifyPostState(list: List<StatisticsEntity>) {
        viewStateMutable.value = ViewState.Success(list)
    }
}