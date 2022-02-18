package com.a.vocabulary15.data.repository

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.Statistics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : Repository {

    private val groups = mutableListOf<Group>()
    private val elements = mutableListOf<Element>()
    private val statistics = mutableListOf<Statistics>()

    override suspend fun setGroup(group: Group) {
        groups.add(group)
    }

    override fun getGroups(): Flow<List<Group>> {
        return flow { emit(groups) }
    }

    override suspend fun deleteGroup(idGroup: Int) {
        groups.remove(groups.find { it.id == idGroup })
    }

    override suspend fun setElement(element: Element) {
        elements.add(element)
    }

    override suspend fun getElements(groupId: Int): Flow<List<Element>> {
        return flow { elements.filter { it.id == groupId } }
    }

    override suspend fun deleteElement(id: Int) {
        elements.remove(elements.find { it.id == id })
    }

    override suspend fun editElement(element: Element) {
        elements.add(element)
    }

    override suspend fun setStatistics(thisStatistics: Statistics) {
        statistics.add(thisStatistics)
    }

    override fun getStatistics(): Flow<List<Statistics>>{
        return flow { emit(statistics) }
    }
}