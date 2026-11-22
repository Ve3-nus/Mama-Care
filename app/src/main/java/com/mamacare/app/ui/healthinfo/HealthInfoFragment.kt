package com.mamacare.app.ui.healthinfo

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamacare.app.R
import com.mamacare.app.data.remote.Article
import com.mamacare.app.databinding.FragmentHealthInfoBinding
import com.mamacare.app.ui.MamaCareViewModelFactory
import com.mamacare.app.ui.UiState

/**
 * Health Info screen: shows articles from the API in a RecyclerView.
 * Observes HealthInfoViewModel.articles (LiveData<UiState<List<Article>>>)
 * and switches between loading, content and error views.
 */
class HealthInfoFragment : Fragment() {

    // Data Binding: only valid between onCreateView and onDestroyView
    private var _binding: FragmentHealthInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HealthInfoViewModel
    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel built through Dollan's factory (it needs the Application)
        val factory = MamaCareViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[HealthInfoViewModel::class.java]

        setupRecyclerView()
        observeArticles()

        binding.btnRetry.setOnClickListener { viewModel.loadArticles() }
    }

    private fun setupRecyclerView() {
        adapter = ArticleAdapter { article -> openArticle(article) }
        binding.rvArticles.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticles.adapter = adapter
    }

    private fun observeArticles() {
        viewModel.articles.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> showState(loading = true)
                is UiState.Success -> {
                    adapter.submitList(state.data)
                    showState(content = true)
                }
                is UiState.Error -> {
                    binding.tvError.text = state.message
                    showState(error = true)
                }
            }
        }
    }

    // Shows exactly one of the views and hides the rest
    private fun showState(
        loading: Boolean = false,
        content: Boolean = false,
        error: Boolean = false
    ) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.rvArticles.visibility = if (content) View.VISIBLE else View.GONE
        binding.layoutError.visibility = if (error) View.VISIBLE else View.GONE
        binding.tvEmpty.visibility = View.GONE
    }

    // Implicit intent: opens the article in the user's browser
    private fun openArticle(article: Article) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), R.string.no_browser_found, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoids leaking the views
    }
}