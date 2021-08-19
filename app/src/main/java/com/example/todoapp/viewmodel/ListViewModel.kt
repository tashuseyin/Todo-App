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

    suspend fun deleteData(todoData: TodoData) {
        repository.deleteData(todoData)
    }

    suspend fun insert(todoData: TodoData) {
        repository.insertData(todoData = todoData)
    }

    private fun getAllData(): LiveData<List<TodoData>>? = repository.getAllData

    suspend fun deleteAllData() = repository.deleteAllData()

    fun searchDatabase(query: String): LiveData<List<TodoData>>? =
        repository.searchResultData(query)


    fun sortByHighPriority(): LiveData<List<TodoData>>? = repository.sortByHighPriority()
    fun sortByLowPriority(): LiveData<List<TodoData>>? = repository.sortByLowPriority()



}