package com.example.monotodo.network

// 名言教えるよAPIのJSONキー"meigen"と"auther"に準拠したdata class
data class Meigen(
    val meigen: String,
    val auther: String
)
