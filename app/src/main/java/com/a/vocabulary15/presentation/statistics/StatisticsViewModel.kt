package com.a.vocabulary15.presentation.statistics

import androidx.lifecycle.ViewModel
import com.a.vocabulary15.domain.usecases.GetStatistics
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel@Inject constructor(
    getStatistics: GetStatistics
): ViewModel() {
    val statistics = getStatistics.invoke()
}