package com.example.clubmanager.data.repo;

import com.example.clubmanager.data.models.Ping;
import com.example.clubmanager.data.models.Staff;

import java.util.List;

public interface Repo {
    void addPing(Ping ping);
    List<Ping> getPings();
    List<Staff> getStaff();
    List<String> getRoles();
}
