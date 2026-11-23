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