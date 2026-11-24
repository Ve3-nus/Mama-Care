package com.mamacare.app.data.local
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mamacare.app.data.local.dao.HealthLogDao
import com.mamacare.app.data.local.dao.MedicationDao
import com.mamacare.app.data.local.dao.UserDao
import com.mamacare.app.data.local.entity.HealthLog
import com.mamacare.app.data.local.entity.Medication
import com.mamacare.app.data.local.entity.User

@Database(
    entities = [Medication::class, HealthLog::class, User::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun medicationDao(): MedicationDao
    abstract fun healthLogDao(): HealthLogDao
    abstract fun userDao(): UserDao

    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `users` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`name` TEXT NOT NULL, " +
                        "`email` TEXT NOT NULL, " +
                        "`password_hash` TEXT NOT NULL, " +
                        "`salt` TEXT NOT NULL, " +
                        "`created_at` INTEGER NOT NULL)"
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_users_email` ON `users` (`email`)")
            }
        }

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mamacare_db"
                ).addMigrations(MIGRATION_1_2).build().also { INSTANCE = it }
            }
        }
    }
}
