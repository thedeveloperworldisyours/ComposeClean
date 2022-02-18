package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.Repository

class GetStatisticsImpl(private val repository: Repository) : GetStatistics {
    override operator fun invoke() = repository.getStatistics()
}