package com.example.clubmanager.ui.ping;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubmanager.R;
import com.example.clubmanager.data.models.Ping;
import com.example.clubmanager.data.repo.ServiceLocator;

import java.util.ArrayList;
import java.util.UUID;

public class ComposePingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_ping);

        // ✅ Enable back arrow in the top bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Compose Ping");
        }

        // Example ping send button logic
        findViewById(R.id.btnSend).setOnClickListener(v -> {
            try {
                // Example data for now — replace with your actual form fields
                String msg = "Test Ping";
                Ping ping = new Ping(
                        UUID.randomUUID().toString(),
                        null,                   // target (replace with your value)
                        new ArrayList<>(),      // toIds
                        msg,
                        null,                   // urgency (replace if applicable)
                        System.currentTimeMillis(),
                        new ArrayList<>()
                );

                ServiceLocator.repo.addPing(ping);
                Toast.makeText(this, "Ping sent!", Toast.LENGTH_SHORT).show();
                finish(); // Close after sending
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // ✅ Handle back arrow click
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
