package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Statistics
import kotlinx.coroutines.flow.Flow

interface GetStatistics {
    operator fun invoke(): Flow<List<Statistics>>
}