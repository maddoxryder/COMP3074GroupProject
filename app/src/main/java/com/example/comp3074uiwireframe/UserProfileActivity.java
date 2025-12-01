package com.example.comp3074uiwireframe;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Back button
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Get role passed from HomeActivity
        String role = getIntent().getStringExtra("userRole");

        TextView tvRole = findViewById(R.id.tvUserRole);
        TextView tvName = findViewById(R.id.tvUserName);

        // Set name based on role
        if ("manager".equalsIgnoreCase(role)) {
            tvName.setText("Rock L.");
        } else if ("staff".equalsIgnoreCase(role)) {
            tvName.setText("Arthur L.");
        } else {
            tvName.setText("User");
        }

        tvRole.setText(role);


        Button btnEdit = findViewById(R.id.btnEditProfile);

        btnEdit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);

            final EditText input = new EditText(UserProfileActivity.this);
            input.setText(tvName.getText().toString());
            input.setSelection(input.getText().length());

            builder.setTitle("Edit Name");
            builder.setView(input);

            builder.setPositiveButton("Save", (dialog, which) -> {
                String newName = input.getText().toString().trim();
                if (!newName.isEmpty()) {
                    tvName.setText(newName);
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });
    }
}
