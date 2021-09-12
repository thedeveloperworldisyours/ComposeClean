package com.a.vocabulary15.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ElementData(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "groupId") var groupId: Int?,
    @ColumnInfo(name = "image") var image: String?,
    @ColumnInfo(name = "value") var value: String,
    @ColumnInfo(name = "link") var link: String
)