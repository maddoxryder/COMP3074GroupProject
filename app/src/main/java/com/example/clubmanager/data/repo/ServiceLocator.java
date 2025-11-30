package com.example.clubmanager.data.repo;

public final class ServiceLocator {
    private ServiceLocator() {}
    public static Repo repo = new InMemoryRepo();
}
