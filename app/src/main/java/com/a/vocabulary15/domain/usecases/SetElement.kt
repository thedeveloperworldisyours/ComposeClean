package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Element

interface SetElement {
    suspend fun invoke(element: Element)
}