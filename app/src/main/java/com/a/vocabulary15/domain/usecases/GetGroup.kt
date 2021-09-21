package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.GroupElementStates

interface GetGroup {
    suspend operator fun invoke(): GroupElementStates
}