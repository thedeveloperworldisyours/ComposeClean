package com.a.vocabulary15.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VocabularyDao {

    @Query("SELECT * FROM ElementData")
    fun getElements(): List<ElementData>

    @Query("SELECT * FROM ElementData WHERE groupId= :groupId")
    fun getElementsByCollection(groupId: Int): List<ElementData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setElement(elementData: ElementData): Long

    @Query("DELETE FROM ElementData WHERE groupId= :groupId")
    fun deleteElementsByGroupId(groupId: Int)

    //Group
    @Query("SELECT * FROM GroupData")
    fun getGroup(): List<GroupData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setGroup(groupData: GroupData): Long

    @Query("DELETE FROM GroupData WHERE id= :id")
    fun deleteGroupById(id: Int)
}