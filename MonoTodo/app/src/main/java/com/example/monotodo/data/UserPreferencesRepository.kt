package com.example.monotodo.data

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val isMeigenDisplayEnabled: Flow<Boolean>
    suspend fun setMeigenDisplayEnabled(isMeigenDisplayEnabled: Boolean)
}
