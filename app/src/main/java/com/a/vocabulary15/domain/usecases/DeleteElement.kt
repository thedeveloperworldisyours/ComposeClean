package com.a.vocabulary15.domain.usecases

interface DeleteElement{
    suspend fun invoke(id: Int)
}