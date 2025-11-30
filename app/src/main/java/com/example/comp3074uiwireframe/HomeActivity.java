package com.example.comp3074uiwireframe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.comp3074uiwireframe.R; // This should be your app's R
// Make sure there is NO "import android.R;"


public class HomeActivity extends AppCompatActivity {

    private Button btnInventory, btnOrders, btnTasks, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String userRole = getIntent().getStringExtra("userRole");


        // Find the bottom buttons
        btnInventory = findViewById(R.id.btnInventory);
        btnOrders = findViewById(R.id.btnOrders);
        btnTasks = findViewById(R.id.btnTasks);
        btnSettings = findViewById(R.id.btnSettings);

        // Tasks screen — pass role to TasksActivity
        btnTasks.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TasksActivity.class);
            intent.putExtra("userRole", userRole);
            startActivity(intent);
        });


        // Inventory (placeholder)
        btnInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, InventoryActivity.class);
                startActivity(intent);
            }
        });

        // Orders screen
        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });


        // Settings (placeholder)
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        });


        // Logout
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.imgProfile).setOnClickListener(v -> openProfile(userRole));
        findViewById(R.id.tvProfile).setOnClickListener(v -> openProfile(userRole));
    }

    private void openProfile(String userRole) {
        Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
        intent.putExtra("userRole", userRole);
        startActivity(intent);

    }

}