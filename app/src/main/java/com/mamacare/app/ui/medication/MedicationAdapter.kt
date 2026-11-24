package com.mamacare.app.ui.medication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mamacare.app.data.local.entity.Medication
import com.mamacare.app.databinding.ItemMedicationBinding

class MedicationAdapter(
    private val onMedicationClick: (Medication) -> Unit = {},
    private val onDeleteClick: (Medication) -> Unit = {}
    private val onItemClick: (Medication) -> Unit,
    private val onDeleteClick: (Medication) -> Unit
) : ListAdapter<Medication, MedicationAdapter.MedicationViewHolder>(MedicationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val binding = ItemMedicationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MedicationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        holder.bind(getItem(position), onMedicationClick, onDeleteClick)
        holder.bind(getItem(position), onItemClick, onDeleteClick)
    }

    class MedicationViewHolder(
        private val binding: ItemMedicationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            medication: Medication,
            onMedicationClick: (Medication) -> Unit,

        fun bind(
            medication: Medication,
            onItemClick: (Medication) -> Unit,
            onDeleteClick: (Medication) -> Unit
        ) {
            binding.medication = medication
            binding.executePendingBindings()
            binding.root.setOnClickListener { onMedicationClick(medication) }

            binding.root.setOnClickListener { onItemClick(medication) }
            binding.btnDeleteMedication.setOnClickListener { onDeleteClick(medication) }
        }
    }

    class MedicationDiffCallback : DiffUtil.ItemCallback<Medication>() {
        override fun areItemsTheSame(oldItem: Medication, newItem: Medication): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Medication, newItem: Medication): Boolean =
            oldItem == newItem
    }
}
