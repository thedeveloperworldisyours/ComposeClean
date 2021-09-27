package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates

interface SetElement {
    suspend fun invoke(element: Element): GroupElementStates<Long>
}