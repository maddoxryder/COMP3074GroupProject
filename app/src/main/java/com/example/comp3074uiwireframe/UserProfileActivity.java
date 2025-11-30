package com.example.comp3074uiwireframe;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        String role = getIntent().getStringExtra("userRole");

        TextView tvRole = findViewById(R.id.tvUserRole);
        TextView tvName = findViewById(R.id.tvUserName);

        // Dummy data for now — you can later load real user info from DB, API, or SharedPreferences
        if ("manager".equalsIgnoreCase(role)) {
            tvName.setText("Rock L.");
        } else if ("staff".equalsIgnoreCase(role)) {
            tvName.setText("Arthur L.");
        } else {
            tvName.setText("User");
        }

        tvRole.setText(role);
    }
}
