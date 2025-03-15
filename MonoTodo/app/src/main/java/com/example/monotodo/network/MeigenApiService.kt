package com.example.monotodo.network

import retrofit2.http.GET

interface MeigenApiService {
    @GET("json.php")
    suspend fun getRandomMeigen(): List<Meigen>
}
