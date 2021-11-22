package com.a.vocabulary15.data.repository

import com.a.vocabulary15.data.local.VocabularyDatabase
import com.a.vocabulary15.data.toData
import com.a.vocabulary15.data.toModel
import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates

class RepositoryImpl constructor(
    vocabularyDatabase: VocabularyDatabase
) : Repository {

    var vocabularyDao = vocabularyDatabase.vocabularyDao()

    override suspend fun setGroup(group: Group): GroupElementStates<List<Group>> {
        GroupElementStates.Data(vocabularyDao.setGroup(group.toData()))
        return GroupElementStates.Data(vocabularyDao.getGroup().map { it.toModel() })
    }

    override suspend fun getGroup() =
        GroupElementStates.Data(vocabularyDao.getGroup().map { it.toModel() })

    override suspend fun deleteGroup(idGroup: Int) {
        vocabularyDao.deleteElementsByGroupId(idGroup)
        vocabularyDao.deleteGroupById(idGroup)
    }

    override suspend fun setElement(element: Element): GroupElementStates<List<Element>> {
        GroupElementStates.Data(vocabularyDao.setElement(element.toData()))
        return GroupElementStates.Data(vocabularyDao.getElements().map { it.toModel() })
    }

    override suspend fun getElement() = GroupElementStates.Data(vocabularyDao.getElements().map { it.toModel() })

    override suspend fun deleteElement(id: Int): GroupElementStates<List<Element>> {
        vocabularyDao.deleteElement(id)
        return getElement()
    }

    override suspend fun editElement(element: Element): GroupElementStates<List<Element>> {
        vocabularyDao.editElement(element.id, element.value, element.link)
        return GroupElementStates.Data(vocabularyDao.getElements().map { it.toModel() })
    }


}