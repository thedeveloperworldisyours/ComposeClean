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

    override suspend fun setGroup(group: Group) =
        GroupElementStates.Data(vocabularyDao.setGroup(group.toData()))

    override suspend fun getGroup() =
        GroupElementStates.Data(vocabularyDao.getGroup().map { it.toModel() })

    override suspend fun setElement(element: Element) =
        GroupElementStates.Data(vocabularyDao.setElement(element.toData()))


}