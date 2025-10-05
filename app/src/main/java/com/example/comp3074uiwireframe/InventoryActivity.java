package com.example.comp3074uiwireframe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InventoryActivity extends AppCompatActivity {

    private LinearLayout itemContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        itemContainer = findViewById(R.id.itemContainer);

        // Back button closes the activity
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // Dummy stock items
        String[] items = {"Item #1", "Item #2", "Item #3", "Item #4", "Item #5"};
        int[] stock = {100, 80, 50, 30, 75};

        for (int i = 0; i < items.length; i++) {
            addStockItem(items[i], stock[i]);
        }
    }

    // Dynamically add each stock item row
    private void addStockItem(String name, int quantity) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(8, 8, 8, 8);

        TextView tvName = new TextView(this);
        tvName.setText(name);
        tvName.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 2f));

        Button btnDecrease = new Button(this);
        btnDecrease.setText("–");

        TextView tvQuantity = new TextView(this);
        tvQuantity.setText(String.valueOf(quantity));
        tvQuantity.setPadding(16, 0, 16, 0);

        Button btnIncrease = new Button(this);
        btnIncrease.setText("+");

        // Button click logic
        btnIncrease.setOnClickListener(v -> {
            int qty = Integer.parseInt(tvQuantity.getText().toString());
            tvQuantity.setText(String.valueOf(qty + 1));
        });

        btnDecrease.setOnClickListener(v -> {
            int qty = Integer.parseInt(tvQuantity.getText().toString());
            if (qty > 0) {
                tvQuantity.setText(String.valueOf(qty - 1));
            }
        });

        row.addView(tvName);
        row.addView(btnDecrease);
        row.addView(tvQuantity);
        row.addView(btnIncrease);

        itemContainer.addView(row);
    }
}
