package com.example.todoapp.model.database

import androidx.room.TypeConverter
import com.example.todoapp.model.entities.Priority

class TodoTypeConverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String{
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority{
        return Priority.valueOf(priority)
    }

}