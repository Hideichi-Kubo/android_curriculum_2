package com.example.monotodo.data

import android.content.Context
import com.example.monotodo.network.Meigen
import com.example.monotodo.network.MeigenApiService

class DefaultMeigenRepository(
    private val meigenApiService: MeigenApiService,
    private val context: Context
) : MeigenRepository {
    // 一度取得した名言リストをキャッシュし、不要なAPIリクエストを防止する
    private var cachedMeigenList: List<Meigen>? = null

    override suspend fun getRandomMeigenFromApiOrLocal(): List<Meigen> {
        // キャッシュ済みならば再取得せずにそのまま返す
        cachedMeigenList?.let { return it }

        if (!isJapanese(context)) {
            val localMeigenList = fallbackToLocal()
            cachedMeigenList = localMeigenList
            return localMeigenList
        }

        return try {
            val apiMeigenList = meigenApiService.getRandomMeigen()
            val meigenList = apiMeigenList.ifEmpty {
                fallbackToLocal()
            }

            cachedMeigenList = meigenList
            meigenList
        } catch (e: Exception) {
            val localMeigenList = fallbackToLocal()
            cachedMeigenList = localMeigenList
            localMeigenList
        }
    }

    private fun fallbackToLocal(): List<Meigen> {
        val localMeigens = LocalMeigenData.getFallbackMeigens(context)
        val randomMeigen = localMeigens.random()
        return listOf(randomMeigen)
    }

    private fun isJapanese(context: Context): Boolean {
        val locale = context.resources.configuration.locales[0]
        return locale.language == "ja"
    }
}