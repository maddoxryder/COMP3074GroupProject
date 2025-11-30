package com.example.clubmanager.data.repo;

import com.example.clubmanager.data.models.Ping;
import com.example.clubmanager.data.models.Staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryRepo implements Repo {
    private final List<Ping> pings = new ArrayList<>();
    private final List<Staff> staff = new ArrayList<>(Arrays.asList(
            new Staff("s1", "Alicia", "Bartender"),
            new Staff("s2", "Myles", "Security"),
            new Staff("s3", "Maddox", "Manager"),
            new Staff("s4", "Aaron", "Floor")
    ));
    private final List<String> roles = new ArrayList<>(Arrays.asList(
            "Bartender", "Security", "Manager", "Floor"
    ));

    @Override public void addPing(Ping ping) { pings.add(ping); }
    @Override public List<Ping> getPings() { return new ArrayList<>(pings); }
    @Override public List<Staff> getStaff() { return new ArrayList<>(staff); }
    @Override public List<String> getRoles() { return new ArrayList<>(roles); }
}
