package com.example.todoapp.app

import android.app.Application
import com.example.todoapp.model.database.TodoDatabase
import kotlinx.coroutines.InternalCoroutinesApi

class App : Application() {
    @InternalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        TodoDatabase.initializeDatabase(applicationContext)
    }
}