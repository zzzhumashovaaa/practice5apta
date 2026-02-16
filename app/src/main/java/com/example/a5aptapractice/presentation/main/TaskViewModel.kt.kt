package com.example.a5aptapractice.presentation.main

import androidx.lifecycle.ViewModel
import com.example.a5aptapractice.domain.model.TaskItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<TaskItem>>(
        listOf(
            TaskItem(1, "Task #1", false),
            TaskItem(2, "Task #2", false),
            TaskItem(3, "Task #3", false)
        )
    )
    val tasks: StateFlow<List<TaskItem>> = _tasks.asStateFlow()

    private var nextId = 4L
    private var selectedId: Long? = null

    fun addItem() {
        val current = _tasks.value.toMutableList()
        current.add(0, TaskItem(nextId, "Task #$nextId", false))
        nextId++
        _tasks.value = current
    }

    fun selectItem(item: TaskItem) {
        selectedId = item.id
        _tasks.value = _tasks.value.map {
            if (it.id == item.id) it.copy(isDone = true) else it.copy(isDone = false)
        }
    }

    fun updateSelectedTitle(newTitle: String) {
        val id = selectedId ?: return
        if (newTitle.isBlank()) return

        _tasks.value = _tasks.value.map {
            if (it.id == id) it.copy(title = newTitle) else it
        }
    }

    fun hasSelection(): Boolean = selectedId != null

    fun deleteSelected() {
        val id = selectedId ?: return
        _tasks.value = _tasks.value.filterNot { it.id == id }
        selectedId = null
    }
}
