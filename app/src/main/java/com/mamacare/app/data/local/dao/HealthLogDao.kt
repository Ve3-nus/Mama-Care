package com.mamacare.app.data.local.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mamacare.app.data.local.entity.HealthLog

@Dao
interface HealthLogDao {

    @Insert
    suspend fun insert(log: HealthLog): Long

    @Update
    suspend fun update(log: HealthLog)

    @Delete
    suspend fun delete(log: HealthLog)

    @Query("SELECT * FROM health_logs ORDER BY dateMillis DESC")
    fun getAll(): LiveData<List<HealthLog>>

    @Query("SELECT * FROM health_logs WHERE id = :id")
    suspend fun getById(id: Int): HealthLog?
}
