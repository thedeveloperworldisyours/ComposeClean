package com.a.vocabulary15.domain.usecases

interface DeleteGroupWithElements {
    suspend fun invoke(idGroup: Int)
}