package com.example.a5aptapractice.presentation.main

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a5aptapractice.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TaskListAdapter { item ->
            viewModel.selectItem(item)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.tasks.collectLatest { list ->
                adapter.submitList(list)
            }
        }

        binding.btnAdd.setOnClickListener {
            viewModel.addItem()
        }

        binding.btnUpdate.setOnClickListener {
            if (!viewModel.hasSelection()) {
                Toast.makeText(this, "Алдымен таскты таңдаңыз", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val input = EditText(this).apply {
                hint = "Жаңа атау енгізіңіз"
                setPadding(40, 30, 40, 30)
            }

            AlertDialog.Builder(this)
                .setTitle("Task өзгерту")
                .setMessage("Жаңа мәтінді жазыңыз:")
                .setView(input)
                .setPositiveButton("Сақтау") { _, _ ->
                    val newText = input.text.toString().trim()
                    viewModel.updateSelectedTitle(newText)
                }
                .setNegativeButton("Бас тарту", null)
                .show()
        }

        binding.btnDelete.setOnClickListener {
            if (!viewModel.hasSelection()) {
                Toast.makeText(this, "Алдымен таскты таңдаңыз", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.deleteSelected()
        }
    }
}
