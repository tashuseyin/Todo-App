package com.example.todoapp.model.database

import android.content.Context
import androidx.room.*
import com.example.todoapp.model.entities.TodoData
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


@Database(entities = [TodoData::class], version = 1)
@TypeConverters(TodoTypeConverter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun initializeDatabase(context: Context) {
            kotlin.synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).build()
            }
        }

        fun getDatabase() = INSTANCE
    }
}