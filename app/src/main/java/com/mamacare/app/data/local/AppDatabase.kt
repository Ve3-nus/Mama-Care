package com.mamacare.app.data.local
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mamacare.app.data.local.dao.HealthLogDao
import com.mamacare.app.data.local.dao.MedicationDao
import com.mamacare.app.data.local.entity.HealthLog
import com.mamacare.app.data.local.entity.Medication

@Database(
    entities = [Medication::class, HealthLog::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun medicationDao(): MedicationDao
    abstract fun healthLogDao(): HealthLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mamacare_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
