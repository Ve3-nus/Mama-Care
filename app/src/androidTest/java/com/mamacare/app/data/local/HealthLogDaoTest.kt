package com.mamacare.app.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mamacare.app.data.local.dao.HealthLogDao
import com.mamacare.app.data.local.entity.HealthLog
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HealthLogDaoTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var db: AppDatabase
    private lateinit var dao: HealthLogDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.healthLogDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertHealthLog_canBeReadBack() = runBlocking {
        val id = dao.insert(
            HealthLog(weightKg = 65.5, systolic = 120, diastolic = 80)
        )
        val result = dao.getById(id.toInt())
        assertEquals(65.5, result?.weightKg ?: 0.0, 0.001)
        assertEquals(120, result?.systolic)
    }

    @Test
    fun deleteHealthLog_removesIt() = runBlocking {
        val id = dao.insert(
            HealthLog(weightKg = 70.0, systolic = 118, diastolic = 76)
        ).toInt()
        dao.delete(dao.getById(id)!!)
        assertNull(dao.getById(id))
    }
}
