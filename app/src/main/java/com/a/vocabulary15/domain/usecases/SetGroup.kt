package com.a.vocabulary15.domain.usecases

import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementState

interface SetGroup {
    suspend operator fun invoke(group: Group): GroupElementState
}