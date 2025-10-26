// Ping.java
package com.example.clubmanager.data.models;

import java.util.ArrayList;
import java.util.List;

public class Ping {
    private final String id;
    private final TargetType target;
    private final List<String> toIds;        // recipients
    private final String message;
    private final Urgency urgency;
    private final long createdAtMillis;      // timestamp
    private final List<String> acknowledged; // who has acknowledged

    // Existing 5-arg constructor (keep this as-is)
    public Ping(String id, TargetType target, String message, Urgency urgency, long createdAtMillis) {
        this(id, target, /*toIds*/ new ArrayList<>(), message, urgency, createdAtMillis, new ArrayList<>());
    }

    // ✅ New 7-arg overload to match your call in ComposePingActivity
    public Ping(String id,
                TargetType target,
                List<String> toIds,
                String message,
                Urgency urgency,
                long createdAtMillis,
                List<String> acknowledged) {
        this.id = id;
        this.target = target;
        this.toIds = (toIds != null) ? toIds : new ArrayList<>();
        this.message = message;
        this.urgency = urgency;
        this.createdAtMillis = createdAtMillis;
        this.acknowledged = (acknowledged != null) ? acknowledged : new ArrayList<>();
    }

    // getters (add what you need)
    public String getId() { return id; }
    public TargetType getTarget() { return target; }
    public List<String> getToIds() { return toIds; }
    public String getMessage() { return message; }
    public Urgency getUrgency() { return urgency; }
    public long getCreatedAtMillis() { return createdAtMillis; }
    public List<String> getAcknowledged() { return acknowledged; }
}
