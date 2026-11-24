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
                    else UiState.Success(list)
                )
            } catch (e: IOException) {
                _articles.postValue(UiState.Error("No internet connection. Check your network and retry."))
            } catch (e: Exception) {
                _articles.postValue(UiState.Error("Something went wrong. Please try again."))
            }
        }
    }
}
