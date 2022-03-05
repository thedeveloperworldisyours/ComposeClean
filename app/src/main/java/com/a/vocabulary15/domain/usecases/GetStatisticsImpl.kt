package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.Repository
import kotlinx.coroutines.flow.map

class GetStatisticsImpl(private val repository: Repository) : GetStatistics {
    override operator fun invoke() = repository.getStatistics().map { statistics ->
        statistics.sortedByDescending {it.date}
    }
}