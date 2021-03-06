package com.a.vocabulary15.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StatisticsData(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "date") var date: Long = 0,
    @ColumnInfo(name = "points") var points: Int,
    @ColumnInfo(name = "groupId") var groupId: Int?)
