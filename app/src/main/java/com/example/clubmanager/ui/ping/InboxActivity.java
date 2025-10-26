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

        // Connect the ListView from layout
        ListView list = findViewById(R.id.listPings);

        // Retrieve all pings from the repo
        List<Ping> pings = ServiceLocator.repo.getPings();

        // Format for displaying time
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        // Create a readable list of strings for display
        List<String> rows = new ArrayList<>();

        for (Ping p : pings) {
            // Format the creation time (convert millis to readable time)
            String when = df.format(new Date(p.getCreatedAtMillis()));

            // Retrieve target type (or however you want to display it)
            String targetLabel = p.getTarget() != null ? p.getTarget().name() : "Unknown";

            // Combine information into one line per Ping
            String displayText = when + " • " + targetLabel + " • " + p.getMessage();
            rows.add(displayText);
        }

        // Attach the data to the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                rows
        );
        list.setAdapter(adapter);
    }
}
