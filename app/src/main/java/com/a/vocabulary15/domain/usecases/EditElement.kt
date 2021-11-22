package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates

interface EditElement {
    suspend fun invoke(element: Element): GroupElementStates<List<Element>>
}