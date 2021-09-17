package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates

interface GetGroups {
    suspend fun invoke() : GroupElementStates<List<Group>>
}