package com.a.vocabulary15.presentation.statistics

import androidx.lifecycle.ViewModel
import com.a.vocabulary15.domain.usecases.GetGroups
import com.a.vocabulary15.domain.usecases.GetStatistics
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel@Inject constructor(
    getStatistics: GetStatistics,
    getGroups: GetGroups
): ViewModel() {
    val groups = getGroups.invoke()
    val statistics = getStatistics.invoke()
}