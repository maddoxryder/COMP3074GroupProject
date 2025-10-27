package com.example.clubmanager.ui.ping;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubmanager.R;
import com.example.clubmanager.data.models.Ping;
import com.example.clubmanager.data.repo.ServiceLocator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        // ✅ Enable back arrow in the top bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ping Inbox");
        }

        // Retrieve and display pings
        ListView list = findViewById(R.id.listPings);
        List<Ping> pings = ServiceLocator.repo.getPings();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        List<String> rows = new ArrayList<>();

        for (Ping p : pings) {
            String when = df.format(new Date(p.getCreatedAtMillis()));
            String targetLabel = (p.getTarget() != null) ? p.getTarget().name() : "General";
            rows.add(when + " • " + targetLabel + " • " + p.getMessage());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                rows
        );

        list.setAdapter(adapter);
    }

    // ✅ Handle back arrow click
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
