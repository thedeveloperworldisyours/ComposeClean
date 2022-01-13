package com.a.vocabulary15.presentation.group

import com.a.vocabulary15.domain.model.Group

data class GroupState(
    val groups: List<Group> = emptyList()
)
