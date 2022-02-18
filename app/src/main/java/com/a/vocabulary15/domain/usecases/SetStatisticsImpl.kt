package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Statistics

class SetStatisticsImpl(private val repository: Repository): SetStatistics {
    override suspend fun invoke(statistics: Statistics) {
        repository.setStatistics(statistics)
    }
}