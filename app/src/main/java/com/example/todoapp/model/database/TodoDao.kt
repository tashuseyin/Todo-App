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

    @Query("SELECT * FROM todo_table WHERE title LIKE :query")
    fun searchDatabase(query: String): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L% ' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<TodoData>>


    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table WHERE priority = Lower(:filterType) or priority = :filterType")
    fun getFilteredDishesList(filterType: String): LiveData<List<TodoData>>
}