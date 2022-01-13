package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.Repository

class GetGroupsImpl(private val repository: Repository): GetGroups{
    override suspend fun invoke() = repository.getGroups()
}