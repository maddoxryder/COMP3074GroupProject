package com.example.clubmanager.ui.ping;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubmanager.R;
import com.example.clubmanager.data.models.Ping;
import com.example.clubmanager.data.models.TargetType;
import com.example.clubmanager.data.models.Urgency;
import com.example.clubmanager.data.repo.ServiceLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ComposePingActivity extends AppCompatActivity {

    private RadioGroup rgTarget;
    private RadioButton rbAll, rbRole, rbStaff;
    private EditText etRole, etStaffIds, etMessage;
    private Spinner spUrgency;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_ping);

        rgTarget = findViewById(R.id.rgTarget);
        rbAll = findViewById(R.id.rbAll);
        rbRole = findViewById(R.id.rbRole);
        rbStaff = findViewById(R.id.rbStaff);
        etRole = findViewById(R.id.etRole);
        etStaffIds = findViewById(R.id.etStaffIds);
        etMessage = findViewById(R.id.etMessage);
        spUrgency = findViewById(R.id.spUrgency);
        btnSend = findViewById(R.id.btnSend);

        // Simple urgency spinner
        ArrayAdapter<Urgency> urgAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Urgency.values());
        urgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUrgency.setAdapter(urgAdapter);

        // Toggle role/staff fields visibility
        rgTarget.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbRole) {
                etRole.setVisibility(View.VISIBLE);
                etStaffIds.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbStaff) {
                etRole.setVisibility(View.GONE);
                etStaffIds.setVisibility(View.VISIBLE);
            } else {
                etRole.setVisibility(View.GONE);
                etStaffIds.setVisibility(View.GONE);
            }
        });

        btnSend.setOnClickListener(v -> onSend());
    }

    private void onSend() {
        // target
        TargetType target;
        if (rbAll.isChecked()) {
            target = TargetType.ALL;
        } else if (rbRole.isChecked()) {
            target = TargetType.ROLE;
        } else {
            target = TargetType.STAFF;
        }

        // validate message
        String msg = etMessage.getText() != null ? etMessage.getText().toString().trim() : "";
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(this, "Message required", Toast.LENGTH_SHORT).show();
            return;
        }

        // toIds depends on target
        List<String> toIds = new ArrayList<>();
        if (target == TargetType.ROLE) {
            String role = etRole.getText() != null ? etRole.getText().toString().trim() : "";
            if (TextUtils.isEmpty(role)) {
                Toast.makeText(this, "Enter a role", Toast.LENGTH_SHORT).show();
                return;
            }
            toIds.add(role); // using role string here; your repo can interpret it
        } else if (target == TargetType.STAFF) {
            String raw = etStaffIds.getText() != null ? etStaffIds.getText().toString().trim() : "";
            if (TextUtils.isEmpty(raw)) {
                Toast.makeText(this, "Pick at least one staff", Toast.LENGTH_SHORT).show();
                return;
            }
            for (String s : raw.split(",")) {
                String id = s.trim();
                if (!id.isEmpty()) toIds.add(id);
            }
        }

        Urgency urgency = (Urgency) spUrgency.getSelectedItem();

        Ping ping = new Ping(
                UUID.randomUUID().toString(),
                target,
                toIds,
                msg,
                urgency,
                System.currentTimeMillis(),
                new ArrayList<>()
        );

        ServiceLocator.repo.addPing(ping);
        Toast.makeText(this, "Ping sent", Toast.LENGTH_SHORT).show();
        finish();
    }
}
