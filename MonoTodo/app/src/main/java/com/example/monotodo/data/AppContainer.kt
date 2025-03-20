package com.example.monotodo.data

import android.content.Context
import com.example.monotodo.network.MeigenApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val tasksRepository: TasksRepository
    val meigenRepository: MeigenRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    override val tasksRepository: TasksRepository by lazy {
        DefaultTasksRepository(MonoTodoDatabase.getDatabase(context).taskDao())
    }

    companion object {
        private const val BASE_URL = "https://meigen.doodlenote.net/api/"
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: MeigenApiService by lazy {
        retrofit.create(MeigenApiService::class.java)
    }

    override val meigenRepository: MeigenRepository by lazy {
        DefaultMeigenRepository(
            meigenApiService = retrofitService,
            context = context
        )
    }
}
