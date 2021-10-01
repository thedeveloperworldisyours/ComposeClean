package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates

interface GetElement {
    suspend fun invoke(): GroupElementStates<List<Element>>
}