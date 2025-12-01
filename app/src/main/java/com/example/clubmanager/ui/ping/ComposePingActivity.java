package com.example.clubmanager.ui.ping;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp3074uiwireframe.R;
import com.example.clubmanager.data.models.Ping;
import com.example.clubmanager.data.models.TargetType;
import com.example.clubmanager.data.models.Urgency;
import com.example.clubmanager.data.repo.ServiceLocator;

import java.util.ArrayList;
import java.util.UUID;

public class ComposePingActivity extends AppCompatActivity {

    // 🔹 UI fields
    private RadioGroup rgTarget;
    private RadioButton rbAll, rbRole, rbStaff;
    private EditText etRole, etStaffIds, etMessage;
    private Spinner spUrgency;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_ping);

        // ✅ Enable back arrow + title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Compose Ping");
        }

        // ✅ Hook up views
        rgTarget = findViewById(R.id.rgTarget);
        rbAll = findViewById(R.id.rbAll);
        rbRole = findViewById(R.id.rbRole);
        rbStaff = findViewById(R.id.rbStaff);

        etRole = findViewById(R.id.etRole);
        etStaffIds = findViewById(R.id.etStaffIds);
        etMessage = findViewById(R.id.etMessage);

        spUrgency = findViewById(R.id.spUrgency);
        btnSend = findViewById(R.id.btnSend);

        // ✅ Populate urgency spinner (Normal / High / Low)
        ArrayAdapter<String> urgencyAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Normal", "High", "Low"}
        );
        urgencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUrgency.setAdapter(urgencyAdapter);
        spUrgency.setSelection(0); // default: Normal

        // ✅ Show/hide Role / Staff ID fields based on radio selection
        rgTarget.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbRole) {
                etRole.setVisibility(View.VISIBLE);
                etStaffIds.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbStaff) {
                etRole.setVisibility(View.GONE);
                etStaffIds.setVisibility(View.VISIBLE);
            } else {
                // "All staff"
                etRole.setVisibility(View.GONE);
                etStaffIds.setVisibility(View.GONE);
            }
        });

        // Ensure default state (All staff is checked in XML)
        etRole.setVisibility(View.GONE);
        etStaffIds.setVisibility(View.GONE);

        // ✅ Send button logic using real form values
        btnSend.setOnClickListener(v -> {
            // 1) Validate message
            String message = etMessage.getText().toString().trim();
            if (message.isEmpty()) {
                etMessage.setError("Please enter a message");
                etMessage.requestFocus();
                return;
            }

            // 2) Determine target type + recipients
            int checkedId = rgTarget.getCheckedRadioButtonId();
            TargetType targetType;
            ArrayList<String> toIds = new ArrayList<>();

            if (checkedId == R.id.rbRole) {
                // Send by role
                String role = etRole.getText().toString().trim();
                if (role.isEmpty()) {
                    etRole.setError("Please enter a role");
                    etRole.requestFocus();
                    return;
                }
                targetType = TargetType.ROLE;
                // For now we just store the role name in toIds
                toIds.add(role);

            } else if (checkedId == R.id.rbStaff) {
                // Send to specific staff IDs
                String idsText = etStaffIds.getText().toString().trim();
                if (idsText.isEmpty()) {
                    etStaffIds.setError("Please enter at least one staff ID");
                    etStaffIds.requestFocus();
                    return;
                }

                targetType = TargetType.STAFF;
                // Split by comma: "s1, s2, s3"
                String[] parts = idsText.split(",");
                for (String p : parts) {
                    String id = p.trim();
                    if (!id.isEmpty()) {
                        toIds.add(id);
                    }
                }
                if (toIds.isEmpty()) {
                    etStaffIds.setError("Please enter valid staff IDs");
                    etStaffIds.requestFocus();
                    return;
                }

            } else {
                // All staff
                targetType = TargetType.ALL;
                // toIds stays empty
            }

            // 3) Read urgency from spinner
            String selectedUrgency = (String) spUrgency.getSelectedItem();
            Urgency urgency;
            if ("High".equalsIgnoreCase(selectedUrgency)) {
                urgency = Urgency.HIGH;
            } else if ("Low".equalsIgnoreCase(selectedUrgency)) {
                urgency = Urgency.LOW;
            } else {
                urgency = Urgency.NORMAL;
            }

            // 4) Build Ping object and save via repo
            String id = UUID.randomUUID().toString();
            long now = System.currentTimeMillis();

            Ping ping = new Ping(
                    id,
                    targetType,
                    toIds,
                    message,
                    urgency,
                    now,
                    new ArrayList<>()   // nobody has acknowledged yet
            );

            try {
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
