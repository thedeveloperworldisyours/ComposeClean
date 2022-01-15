package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.Repository

class DeleteElementImpl(val repository: Repository): DeleteElement {
    override suspend fun invoke(id: Int) {
        repository.deleteElement(id)
    }
}