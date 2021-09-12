package com.a.vocabulary15.data.repository

import com.a.vocabulary15.data.local.VocabularyDatabase
import com.a.vocabulary15.data.toData
import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.GroupElementStates

class LocalDataSourceImpl constructor(
    vocabularyDatabase: VocabularyDatabase
) : Repository {

    var vocabularyDao = vocabularyDatabase.vocabularyDao()
    override suspend fun setGroup(group: Group) =
        GroupElementStates.InsertGroupData(vocabularyDao.setGroup(group.toData()))
}