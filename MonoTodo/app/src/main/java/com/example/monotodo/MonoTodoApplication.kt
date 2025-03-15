package com.example.monotodo

import android.app.Application
import com.example.monotodo.data.AppContainer
import com.example.monotodo.data.DefaultAppContainer

class MonoTodoApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
