package com.example.comp3074uiwireframe;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class InventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Back button closes activity
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // Load fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new InventoryFragment())
                    .commit();
        }
    }
}
