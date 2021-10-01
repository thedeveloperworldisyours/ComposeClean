package com.a.vocabulary15.domain

import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates

interface Repository {

    suspend fun setGroup(group: Group): GroupElementStates<List<Group>>

    suspend fun getGroup(): GroupElementStates<List<Group>>

    suspend fun deleteGroup(idGroup: Int)

    suspend fun setElement(element: Element): GroupElementStates<List<Element>>

    suspend fun getElement(): GroupElementStates<List<Element>>

}