package com.mamacare.app.data.repository

import androidx.lifecycle.LiveData
import com.mamacare.app.data.local.dao.HealthLogDao
import com.mamacare.app.data.local.dao.MedicationDao
import com.mamacare.app.data.local.entity.HealthLog
import com.mamacare.app.data.local.entity.Medication
import com.mamacare.app.data.remote.Article
import com.mamacare.app.data.remote.NewsService

class MamaCareRepository(
    private val medicationDao: MedicationDao,
    private val healthLogDao: HealthLogDao,
    private val newsService: NewsService
) {
    // Medications (navigation ids are Long, Room ids are Int)
    val medications: LiveData<List<Medication>> = medicationDao.getAll()
    suspend fun getMedication(id: Long): Medication? = medicationDao.getById(id.toInt())
    suspend fun addMedication(m: Medication) = medicationDao.insert(m)
    suspend fun updateMedication(m: Medication) = medicationDao.update(m)
    suspend fun deleteMedication(m: Medication) = medicationDao.delete(m)

    // Health logs
    val healthLogs: LiveData<List<HealthLog>> = healthLogDao.getAll()
    suspend fun getHealthLog(id: Long): HealthLog? = healthLogDao.getById(id.toInt())
    suspend fun addHealthLog(l: HealthLog) = healthLogDao.insert(l)
    suspend fun updateHealthLog(l: HealthLog) = healthLogDao.update(l)
    suspend fun deleteHealthLog(l: HealthLog) = healthLogDao.delete(l)

    // Remote articles (the API wraps the list in NewsResponse)
    suspend fun fetchArticles(): List<Article> =
        newsService.getMaternalHealthNews().articles
}