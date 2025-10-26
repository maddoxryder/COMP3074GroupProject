package com.example.clubmanager.data.repo

object ServiceLocator {
    // swap to Room/Firebase later without touching UI
    var repo: Repo = InMemoryRepo()
}
