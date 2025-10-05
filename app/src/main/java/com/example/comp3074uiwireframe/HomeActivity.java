package com.example.comp3074uiwireframe;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.inventoryButton).setOnClickListener(v ->
                startActivity(new Intent(this, InventoryActivity.class)));

        findViewById(R.id.ordersButton).setOnClickListener(v ->
                startActivity(new Intent(this, OrdersActivity.class)));

        findViewById(R.id.tasksButton).setOnClickListener(v ->
                startActivity(new Intent(this, TasksActivity.class)));
    }
}
