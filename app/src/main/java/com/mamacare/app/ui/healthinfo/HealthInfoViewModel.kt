package com.mamacare.app.ui.healthinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamacare.app.data.remote.Article
import com.mamacare.app.data.repository.MamaCareRepository
import com.mamacare.app.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class HealthInfoViewModel(private val repo: MamaCareRepository) : ViewModel() {

    private val _articles = MutableLiveData<UiState<List<Article>>>()
    val articles: LiveData<UiState<List<Article>>> = _articles

    init { loadArticles() }

        fun loadArticles() {
        _articles.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = repo.fetchArticles()
                _articles.postValue(
                    if (list.isEmpty()) UiState.Error("No articles available yet")
                    else UiState.Success(data = list)
                )
            } catch (e: IOException) {
                // If no internet, load the fallback
                loadFallbackData()
            } catch (e: Exception) {
                // If API fails (401, 429, etc), load the fallback
                loadFallbackData()
            }
        }
    }
    private fun loadFallbackData() {
        val dummyArticles = listOf(
            Article(
                title = "The Importance of Folic Acid",
                description = "Folic acid is crucial for preventing neural tube defects in early pregnancy.",
                url = "https://example.com/1",
                urlToImage = "https://images.unsplash.com/photo-1584036561566-baf8f5f1b144?auto=format&fit=crop&w=400&q=80"
            ),
            Article(
                title = "Staying Hydrated During Pregnancy",
                description = "Drinking enough water helps form amniotic fluid and prevents UTIs.",
                url = "https://example.com/2",
                urlToImage = "https://images.unsplash.com/photo-1523362628745-0c100150b504?auto=format&fit=crop&w=400&q=80"
            ),
            Article(
                title = "Managing Prenatal Appointments",
                description = "Regular checkups help monitor your blood pressure and the baby's growth.",
                url = "https://example.com/3",
                urlToImage = "https://images.unsplash.com/photo-1579684385127-1ef15d508118?auto=format&fit=crop&w=400&q=80"
            )
        )
        
        _articles.postValue(UiState.Success(data = dummyArticles))
    }
}
