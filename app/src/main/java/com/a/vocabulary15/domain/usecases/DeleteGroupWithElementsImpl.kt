package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.Repository

class DeleteGroupWithElementsImpl(private val repository: Repository) : DeleteGroupWithElements {
    override suspend fun invoke(idGroup: Int) {
        repository.deleteGroup(idGroup)
    }

}