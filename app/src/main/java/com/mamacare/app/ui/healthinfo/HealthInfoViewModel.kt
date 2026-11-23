package com.mamacare.app.ui.healthinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamacare.app.data.remote.Article
import com.mamacare.app.data.remote.NewsService
import com.mamacare.app.ui.UiState
import kotlinx.coroutines.launch

class HealthInfoViewModel(private val newsService: NewsService) : ViewModel() {

    private val _articles = MutableLiveData<UiState<List<Article>>>()
    val articles: LiveData<UiState<List<Article>>> get() = _articles

    init {
        loadArticles()
    }

    fun loadArticles() {
        _articles.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = newsService.getMaternalHealthNews()
                if (response.status == "ok") {
                    _articles.value = UiState.Success(response.articles)
                } else {
                    _articles.value = UiState.Error("Failed to fetch articles")
                }
            } catch (e: Exception) {
                _articles.value = UiState.Error(e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }
}
