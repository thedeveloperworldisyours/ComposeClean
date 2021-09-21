package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.Repository

class GetGroupImpl(private val repository: Repository): GetGroup {
    override suspend fun invoke() = repository.getGroup()
}