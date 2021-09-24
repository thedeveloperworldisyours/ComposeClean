package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Element

class SetElementImpl(val repository: Repository):SetElement {
    override suspend fun invoke(element: Element) = repository.setElement(element)
}