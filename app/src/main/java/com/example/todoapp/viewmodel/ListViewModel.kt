package com.example.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.database.TodoRepository
import com.example.todoapp.model.entities.TodoData

class ListViewModel : ViewModel() {

    private val repository = TodoRepository

    private var _currentTodoData: LiveData<List<TodoData>>? = getAllData()
    val currentTodoData = _currentTodoData

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)

    fun checkDatabaseEmpty(todoData: List<TodoData>) {
        emptyDatabase.value = todoData.isEmpty()

    }

    private fun getAllData(): LiveData<List<TodoData>>? = repository.getAllData

    suspend fun deleteAllData() = repository.deleteAllData()

}