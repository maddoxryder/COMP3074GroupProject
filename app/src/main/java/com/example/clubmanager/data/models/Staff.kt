package com.example.clubmanager.data.models

data class Staff(
    val id: String,
    val name: String,
    val role: String,
    var isOnShift: Boolean = true,
    val lastSeen: Long = System.currentTimeMillis()
)
