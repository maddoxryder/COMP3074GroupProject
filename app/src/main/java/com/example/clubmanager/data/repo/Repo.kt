package com.example.clubmanager.data.repo

import com.example.clubmanager.data.models.Ping
import com.example.clubmanager.data.models.Staff

interface Repo {
    val staff: List<Staff>
    val pings: List<Ping>

    fun addPing(p: Ping)
    fun acknowledge(pingId: String, staffId: String)
}
