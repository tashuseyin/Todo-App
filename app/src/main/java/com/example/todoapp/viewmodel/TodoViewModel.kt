package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todoapp.model.database.TodoRepository
import com.example.todoapp.model.entities.TodoData

class TodoViewModel : ViewModel() {

    private val repository = TodoRepository
    
    suspend fun insert(todoData: TodoData) {
        repository.insertData(todoData = todoData)
    }


}