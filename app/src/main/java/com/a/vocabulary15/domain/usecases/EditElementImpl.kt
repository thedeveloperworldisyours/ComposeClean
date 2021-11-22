package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Element

class EditElementImpl(val repository: Repository): EditElement {
    override suspend fun invoke(element: Element)  = repository.editElement(element)
}