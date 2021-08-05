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

    @Update
    suspend fun updateData(todoData: TodoData)

    @Delete
    suspend fun deleteData(todoData: TodoData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllData()
}