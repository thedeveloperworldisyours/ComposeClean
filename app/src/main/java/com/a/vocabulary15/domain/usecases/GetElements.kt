package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Element
import kotlinx.coroutines.flow.Flow

interface GetElements {
    suspend fun invoke(groupId: Int): Flow<List<Element>>
}