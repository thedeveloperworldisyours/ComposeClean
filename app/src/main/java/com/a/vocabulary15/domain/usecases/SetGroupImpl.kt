package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Group

class SetGroupImpl(private val repository: Repository): SetGroup {
    override suspend fun invoke(group: Group){
        repository.setGroup(group)
    }
}