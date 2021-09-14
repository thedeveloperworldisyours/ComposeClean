package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates

interface SetGroup {
    suspend operator fun invoke(group: Group): GroupElementStates<Long>
}