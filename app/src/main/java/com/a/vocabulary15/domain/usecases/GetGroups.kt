package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GetGroups {
    operator fun invoke(): Flow<List<Group>>
}