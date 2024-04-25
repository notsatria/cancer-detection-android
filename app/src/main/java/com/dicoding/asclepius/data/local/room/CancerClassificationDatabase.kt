package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.CancerClassificationEntity

@Database(entities = [CancerClassificationEntity::class], version = 1)
abstract class CancerClassificationDatabase : RoomDatabase() {
    abstract fun dao(): CancerClassificationDao

    companion object {
        @Volatile
        private var INSTANCE: CancerClassificationDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): CancerClassificationDatabase {
            if (INSTANCE == null) {
                synchronized(CancerClassificationDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CancerClassificationDatabase::class.java,
                        "ClassificationResult.db"
                    )
                        .build()
                }
            }
            return INSTANCE as CancerClassificationDatabase
        }
    }
}