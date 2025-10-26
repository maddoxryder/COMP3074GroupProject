package com.example.clubmanager.data.repo

import com.example.clubmanager.data.models.Ping
import com.example.clubmanager.data.models.Staff

class InMemoryRepo : Repo {
    private val staffList = mutableListOf(
        Staff("s1","Alex","Security",  true),
        Staff("s2","Bea","Bartender", true),
        Staff("s3","Cam","Server",    false),
        Staff("s4","Dre","Manager",   true),
    )
    private val pingList = mutableListOf<Ping>()

    override val staff: List<Staff> get() = staffList
    override val pings: List<Ping>  get() = pingList

    override fun addPing(p: Ping) {
        pingList.add(0, p) // newest first
    }

    override fun acknowledge(pingId: String, staffId: String) {
        pingList.firstOrNull { it.id == pingId }?.let { p ->
            if (!p.ackBy.contains(staffId)) p.ackBy.add(staffId)
        }
    }
}
