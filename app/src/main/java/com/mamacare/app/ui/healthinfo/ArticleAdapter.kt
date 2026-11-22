package com.mamacare.app.ui.healthinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mamacare.app.R
import com.mamacare.app.data.remote.Article // TODO (Sync with Brian): verify package
import com.mamacare.app.databinding.ItemArticleBinding

/**
 * Adapter for the Health Info RecyclerView.
 * Uses ListAdapter + DiffUtil so only changed rows are redrawn,
 * and Data Binding to fill each row.
 */
class ArticleAdapter(
    private val onArticleClick: (Article) -> Unit = {}
) : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position), onArticleClick)
    }

    class ArticleViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article, onArticleClick: (Article) -> Unit) {
            binding.article = article
            binding.executePendingBindings()

            // Glide loads the thumbnail; placeholder shows while loading or on failure
            Glide.with(binding.ivArticleImage)
                .load(article.imageUrl)
                .placeholder(R.drawable.ic_launcher_background) // TODO: swap for a proper placeholder
                .error(R.drawable.ic_launcher_background)
                .into(binding.ivArticleImage)

            binding.root.setOnClickListener { onArticleClick(article) }
        }
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        // Same article? (identity check; url is a stable unique key)
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.url == newItem.url

        // Same content? (works because Article is a data class)
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }
}