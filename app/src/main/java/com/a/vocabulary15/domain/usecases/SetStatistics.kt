package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Statistics

interface SetStatistics {
    suspend operator fun invoke(statistics: Statistics)
}