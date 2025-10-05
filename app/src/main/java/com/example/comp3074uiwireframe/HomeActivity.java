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

        // Find the bottom buttons
        btnInventory = findViewById(R.id.btnInventory);
        btnOrders = findViewById(R.id.btnOrders);
        btnTasks = findViewById(R.id.btnTasks);
        btnSettings = findViewById(R.id.btnSettings);

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

        // Tasks screen
        btnTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TasksActivity.class);
                startActivity(intent);
            }
        });

        // Settings (placeholder)
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(HomeActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}