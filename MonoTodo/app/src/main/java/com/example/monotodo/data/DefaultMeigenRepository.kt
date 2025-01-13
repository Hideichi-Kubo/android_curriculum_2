package com.example.monotodo.data

import android.content.Context
import com.example.monotodo.network.Meigen
import com.example.monotodo.network.MeigenApiService

class DefaultMeigenRepository(
    private val meigenApiService: MeigenApiService,
    private val context: Context
) : MeigenRepository {

    override suspend fun getRandomMeigenFromApiOrLocal(): List<Meigen> {
        return try {
            val meigenList = meigenApiService.getRandomMeigen()

            meigenList.ifEmpty {
                fallbackToLocal()
            }
        } catch (e: Exception) {
            fallbackToLocal()
        }
    }

    private fun fallbackToLocal(): List<Meigen> {
        val localMeigens = LocalMeigenData.getFallbackMeigens(context)
        val randomMeigen = localMeigens.random()
        return listOf(randomMeigen)
    }
}