package com.example.todoapp.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoapp.model.entities.TodoData

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(todo: TodoData)

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<TodoData>>
}