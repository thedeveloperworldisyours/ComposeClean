package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates

interface DeleteElement{
    suspend fun invoke(id: Int): GroupElementStates<List<Element>>
}