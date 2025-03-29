package com.example.monotodo.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.monotodo.network.MeigenApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val tasksRepository: TasksRepository
    val meigenRepository: MeigenRepository
    val userPreferencesRepository: UserPreferencesRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    override val tasksRepository: TasksRepository by lazy {
        DefaultTasksRepository(MonoTodoDatabase.getDatabase(context).taskDao())
    }

    companion object {
        private const val BASE_URL = "https://meigen.doodlenote.net/api/"
        private const val PREFERENCES_NAME = "user_preferences"
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

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        DefaultUserPreferencesRepository(context.dataStore)
    }
}
