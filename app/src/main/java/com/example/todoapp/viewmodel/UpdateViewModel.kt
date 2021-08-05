package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todoapp.model.database.TodoRepository
import com.example.todoapp.model.entities.TodoData

class UpdateViewModel : ViewModel() {

    private val repository = TodoRepository

    suspend fun deleteData(todoData: TodoData) {
        repository.deleteData(todoData)
    }

    suspend fun updateData(todoData: TodoData) {
        repository.updateData(todoData)
    }
}