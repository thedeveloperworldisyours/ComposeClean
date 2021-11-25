package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.Repository

class GetElementsImpl(var repository: Repository):GetElements {
    override suspend fun invoke(groupId: Int) = repository.getElements(groupId)
}