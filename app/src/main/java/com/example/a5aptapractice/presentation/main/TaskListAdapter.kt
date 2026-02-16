package com.example.a5aptapractice.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a5aptapractice.databinding.ItemTaskBinding
import com.example.a5aptapractice.domain.model.TaskItem

class TaskListAdapter(
private val onSelect: (TaskItem) -> Unit
) : ListAdapter<TaskItem, TaskListAdapter.VH>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VH(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskItem) {
            binding.tvTitle.text = item.title

            binding.cbDone.setOnCheckedChangeListener(null)
            binding.cbDone.isChecked = item.isDone

            binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onSelect(item)
                }
            }
        }
    }
}

