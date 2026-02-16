package com.example.a5aptapractice.presentation.main

import androidx.recyclerview.widget.DiffUtil
import com.example.a5aptapractice.domain.model.TaskItem

class TaskDiffCallback : DiffUtil.ItemCallback<TaskItem>() {
    override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean =
        oldItem == newItem
}
