package com.a.vocabulary15.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyDao {

    @Query("SELECT * FROM ElementData")
    fun getElements(): List<ElementData>

    @Query("SELECT * FROM ElementData WHERE groupId = :groupId")
    fun getElementsByCollection(groupId: Int): List<ElementData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setElement(elementData: ElementData): Long

    @Query("DELETE FROM ElementData WHERE groupId = :groupId")
    fun deleteElementsByGroupId(groupId: Int)

    @Query("DELETE FROM ElementData WHERE id =:id")
    fun deleteElement(id: Int)

    @Query("UPDATE ElementData SET value = :value, link = :link, image =:image WHERE id =:id")
    fun editElement(id: Int, value: String, link: String, image: String)

    //Group
    @Query("SELECT * FROM GroupData")
    fun getGroup(): List<GroupData>

    @Query("SELECT * FROM GroupData")
    fun getGroups(): Flow<List<GroupData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setGroup(groupData: GroupData): Long

    @Query("DELETE FROM GroupData WHERE id = :id")
    fun deleteGroupById(id: Int)
}