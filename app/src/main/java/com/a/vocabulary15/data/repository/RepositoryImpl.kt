package com.a.vocabulary15.data.repository

import com.a.vocabulary15.data.local.VocabularyDatabase
import com.a.vocabulary15.data.toData
import com.a.vocabulary15.data.toModel
import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementState

class RepositoryImpl constructor(
    vocabularyDatabase: VocabularyDatabase
) : Repository {

    var vocabularyDao = vocabularyDatabase.vocabularyDao()

    override suspend fun setGroup(group: Group): GroupElementState {
        vocabularyDao.setGroup(group.toData())
        return GroupElementState.GroupElementData(vocabularyDao.getGroup().map { it.toModel() })
    }

}