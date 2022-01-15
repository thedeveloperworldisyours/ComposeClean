package com.a.vocabulary15.data.repository

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : Repository {

    private val groups = mutableListOf<Group>()
    private val elements = mutableListOf<Element>()

    override suspend fun setGroup(group: Group) {
        groups.add(group)
    }

    override fun getGroups(): Flow<List<Group>> {
        return flow { emit(groups) }
    }

    override suspend fun deleteGroup(idGroup: Int) {
        groups.remove(groups.find { it.id == idGroup })
    }

    override suspend fun setElement(element: Element): GroupElementStates<List<Element>> {
        elements.add(element)
        return GroupElementStates.Data(elements)
    }

    override suspend fun getElements(groupId: Int): GroupElementStates<List<Element>> {
        return GroupElementStates.Data(elements.filter { it.id == groupId })
    }

    override suspend fun getElement(): GroupElementStates<List<Element>> {
        return GroupElementStates.Data(elements)
    }

    override suspend fun deleteElement(id: Int): GroupElementStates<List<Element>> {
        elements.remove(elements.find { it.id == id })
        return GroupElementStates.Data(elements)
    }

    override suspend fun editElement(element: Element): GroupElementStates<List<Element>> {
        elements.add(element)
        return GroupElementStates.Data(elements)
    }

}