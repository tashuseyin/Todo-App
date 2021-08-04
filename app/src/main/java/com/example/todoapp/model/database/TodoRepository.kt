package com.example.todoapp.model.database

import androidx.lifecycle.LiveData
import com.example.todoapp.model.entities.TodoData

object TodoRepository {

    private val todoDao by lazy {
        TodoDatabase.getDatabase()?.todoDao()
    }

    val getAllData: LiveData<List<TodoData>>? = todoDao?.getAllData()


    suspend fun insertData(todoData: TodoData) {
        todoDao?.insertData(todoData)
    }

}