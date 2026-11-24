package com.mamacare.app.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mamacare.app.data.local.dao.MedicationDao
import com.mamacare.app.data.local.entity.Medication
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MedicationDaoTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var db: AppDatabase
    private lateinit var dao: MedicationDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.medicationDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertMedication_canBeReadBack() = runBlocking {
        val id = dao.insert(
            Medication(name = "Folic Acid", dosage = "5mg", hour = 8, minute = 0)
        )
        val result = dao.getById(id.toInt())
        assertEquals("Folic Acid", result?.name)
    }

    @Test
    fun deleteMedication_removesIt() = runBlocking {
        val id = dao.insert(
            Medication(name = "Iron", dosage = "200mg", hour = 9, minute = 0)
        ).toInt()
        dao.delete(dao.getById(id)!!)
        assertNull(dao.getById(id))
    }
}
