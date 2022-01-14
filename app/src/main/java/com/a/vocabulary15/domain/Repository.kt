package com.a.vocabulary15.domain

import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun setGroup(group: Group): GroupElementStates<List<Group>>

    suspend fun getGroup(): GroupElementStates<List<Group>>

    fun getGroups(): Flow<List<Group>>

    suspend fun deleteGroup(idGroup: Int)

    suspend fun setElement(element: Element): GroupElementStates<List<Element>>

    suspend fun getElements(groupId: Int): GroupElementStates<List<Element>>

    suspend fun getElement(): GroupElementStates<List<Element>>

    suspend fun deleteElement(id: Int): GroupElementStates<List<Element>>

    suspend fun editElement(element: Element): GroupElementStates<List<Element>>

}