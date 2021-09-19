package com.a.vocabulary15.domain

import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementState

interface Repository {

    suspend fun setGroup(group: Group): GroupElementState

}