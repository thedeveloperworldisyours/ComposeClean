package com.a.vocabulary15.domain

import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun setGroup(group: Group)

    fun getGroups(): Flow<List<Group>>

    suspend fun deleteGroup(idGroup: Int)

    suspend fun setElement(element: Element)

    suspend fun getElements(groupId: Int): Flow<List<Element>>

    suspend fun deleteElement(id: Int)

    suspend fun editElement(element: Element)

}