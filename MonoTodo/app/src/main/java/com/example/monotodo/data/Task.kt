package com.example.monotodo.data

data class Task(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)
