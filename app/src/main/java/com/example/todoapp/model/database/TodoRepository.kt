package com.example.todoapp.model.database

import androidx.lifecycle.LiveData
import com.example.todoapp.model.entities.TodoData

object TodoRepository {

    private val todoDao by lazy {
        TodoDatabase.getDatabase()?.todoDao()
    }

    val getAllData: LiveData<List<TodoData>>? = todoDao?.getAllData()

    fun searchResultData(query: String): LiveData<List<TodoData>>? {
        return todoDao?.searchDatabase(query)
    }

    suspend fun updateData(todoData: TodoData) = todoDao?.updateData(todoData)

    suspend fun deleteData(todoData: TodoData) = todoDao?.deleteData(todoData)

    suspend fun insertData(todoData: TodoData) {
        todoDao?.insertData(todoData)
    }

    suspend fun deleteAllData() = todoDao?.deleteAllData()

    fun sortByHighPriority(): LiveData<List<TodoData>>? {
        return todoDao?.sortByHighPriority()
    }

    fun sortByLowPriority(): LiveData<List<TodoData>>? {
        return todoDao?.sortByLowPriority()
    }

    fun filterListData(value: String) = todoDao?.getFilteredDishesList(value)


}