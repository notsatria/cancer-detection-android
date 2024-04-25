package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.CancerClassificationEntity

@Dao
interface CancerClassificationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertResult(entity: CancerClassificationEntity)

    @Query("SELECT * FROM classification_result")
    fun getAllResult(): LiveData<List<CancerClassificationEntity>>
}