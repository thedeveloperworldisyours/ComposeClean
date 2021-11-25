package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates

interface GetElements {
    suspend fun invoke(groupId: Int): GroupElementStates<List<Element>>
}