package com.example.monotodo.data

import com.example.monotodo.network.Meigen

interface MeigenRepository {
    suspend fun getRandomMeigenFromApiOrLocal(): List<Meigen>
}