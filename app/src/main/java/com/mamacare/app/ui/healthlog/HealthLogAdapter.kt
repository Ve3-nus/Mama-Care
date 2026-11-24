package com.mamacare.app.ui.healthlog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mamacare.app.data.local.entity.HealthLog
import com.mamacare.app.databinding.ItemHealthLogBinding

class HealthLogAdapter(
    private val onLogClick: (HealthLog) -> Unit = {},
    private val onDeleteClick: (HealthLog) -> Unit = {}
    private val onItemClick: (HealthLog) -> Unit,
    private val onDeleteClick: (HealthLog) -> Unit
) : ListAdapter<HealthLog, HealthLogAdapter.HealthLogViewHolder>(HealthLogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthLogViewHolder {
        val binding = ItemHealthLogBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HealthLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HealthLogViewHolder, position: Int) {
        holder.bind(getItem(position), onLogClick, onDeleteClick)
        holder.bind(getItem(position), onItemClick, onDeleteClick)
    }

    class HealthLogViewHolder(
        private val binding: ItemHealthLogBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            log: HealthLog,
            onLogClick: (HealthLog) -> Unit,
            onDeleteClick: (HealthLog) -> Unit
        ) {
            binding.healthLog = log
            binding.executePendingBindings()
            binding.root.setOnClickListener { onLogClick(log) }
            binding.btnDeleteLog.setOnClickListener { onDeleteClick(log) }

        fun bind(
            healthLog: HealthLog,
            onItemClick: (HealthLog) -> Unit,
            onDeleteClick: (HealthLog) -> Unit
        ) {
            binding.healthLog = healthLog
            binding.executePendingBindings()

            binding.root.setOnClickListener { onItemClick(healthLog) }
            binding.btnDeleteLog.setOnClickListener { onDeleteClick(healthLog) }
        }
    }

    class HealthLogDiffCallback : DiffUtil.ItemCallback<HealthLog>() {
        override fun areItemsTheSame(oldItem: HealthLog, newItem: HealthLog): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: HealthLog, newItem: HealthLog): Boolean =
            oldItem == newItem
    }
}
