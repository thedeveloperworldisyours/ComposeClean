package com.a.vocabulary15.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GroupData::class, ElementData::class, StatisticsData::class], version = 1)
abstract class VocabularyDatabase : RoomDatabase() {
    abstract fun vocabularyDao(): VocabularyDao

    companion object {
        private var instance: VocabularyDatabase? = null

        fun getDatabase(context: Context): VocabularyDatabase {
            if (instance == null) {
                synchronized(VocabularyDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context,
                            VocabularyDatabase::class.java,
                            "vocabulary.db"
                        )
                            .build()
                    }
                }
            }
            return instance!!
        }
    }
}