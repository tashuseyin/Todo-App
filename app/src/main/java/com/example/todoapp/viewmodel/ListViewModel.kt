package com.example.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.database.TodoRepository
import com.example.todoapp.model.entities.TodoData

class ListViewModel : ViewModel() {

    private val repository = TodoRepository

    private var _currentTodoData: LiveData<List<TodoData>>? = getAllData()
    val currentTodoData = _currentTodoData



    private fun getAllData(): LiveData<List<TodoData>>? = repository.getAllData
}