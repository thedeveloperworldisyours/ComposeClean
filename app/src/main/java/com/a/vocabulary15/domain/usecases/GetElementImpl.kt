package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates

class GetElementImpl(val repository: Repository): GetElement {
    override suspend fun invoke() = repository.getElement()
}