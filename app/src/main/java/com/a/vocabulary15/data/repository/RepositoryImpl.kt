package com.a.vocabulary15.data.repository

import com.a.vocabulary15.data.local.VocabularyDatabase
import com.a.vocabulary15.data.toData
import com.a.vocabulary15.data.toModel
import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl constructor(
    vocabularyDatabase: VocabularyDatabase
) : Repository {

    private var vocabularyDao = vocabularyDatabase.vocabularyDao()

    override suspend fun setGroup(group: Group) {
        vocabularyDao.setGroup(group.toData())
    }

    override fun getGroups(): Flow<List<Group>> =
        vocabularyDao.getGroups().map { list -> list.map { it.toModel() } }


    override suspend fun deleteGroup(idGroup: Int) {
        vocabularyDao.deleteElementsByGroupId(idGroup)
        vocabularyDao.deleteGroupById(idGroup)
    }

    override suspend fun setElement(element: Element) {
        vocabularyDao.setElement(element.toData())
    }

    override suspend fun getElements(groupId: Int) =
        vocabularyDao.getElementsByCollection(groupId).map { list -> list.map { it.toModel() } }

    override suspend fun deleteElement(id: Int) {
        vocabularyDao.deleteElement(id)
    }

    override suspend fun editElement(element: Element) {
        vocabularyDao.editElement(element.id, element.value, element.link, element.image)
    }
}