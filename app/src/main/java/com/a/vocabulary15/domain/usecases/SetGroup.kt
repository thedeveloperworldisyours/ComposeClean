package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Group

interface SetGroup {
    suspend operator fun invoke(group: Group)
}