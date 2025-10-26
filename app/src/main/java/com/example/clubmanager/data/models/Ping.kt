package com.example.clubmanager.data.models

data class Ping(
    val id: String,
    val toType: TargetType,
    val toIds: List<String>,        // ROLE -> [roleName], STAFF -> [staffIds], ALL -> []
    val message: String,
    val urgency: Urgency,
    val createdAt: Long = System.currentTimeMillis(),
    val ackBy: MutableList<String> = mutableListOf()   // staff ids who acknowledged
)
