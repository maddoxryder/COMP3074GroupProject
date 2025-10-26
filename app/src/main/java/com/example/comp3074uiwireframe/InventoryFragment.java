package com.example.comp3074uiwireframe;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InventoryFragment extends Fragment {

    private InventoryViewModel viewModel;
    private InventoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.itemContainer);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAddItem);

        // ViewModel
        viewModel = new ViewModelProvider(this).get(InventoryViewModel.class);

        // Adapter & RecyclerView
        adapter = new InventoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Adapter listener updates Room
        adapter.setOnItemQuantityChangeListener(item -> viewModel.update(item));

        // Observe inventory items
        viewModel.getAllItems().observe(getViewLifecycleOwner(), items -> {
            adapter.setItems(items);
            if (items.isEmpty()) insertSampleData();
        });

        // FAB click
        fabAdd.setOnClickListener(v -> showAddItemDialog());
    }

    private void insertSampleData() {
        viewModel.insert(new InventoryItem("Item #1", 100));
        viewModel.insert(new InventoryItem("Item #2", 80));
        viewModel.insert(new InventoryItem("Item #3", 50));
        viewModel.insert(new InventoryItem("Item #4", 30));
        viewModel.insert(new InventoryItem("Item #5", 75));
    }

    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add New Item");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 16, 32, 16);

        EditText nameInput = new EditText(getContext());
        nameInput.setHint("Item Name");
        layout.addView(nameInput);

        EditText qtyInput = new EditText(getContext());
        qtyInput.setHint("Quantity");
        qtyInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(qtyInput);

        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = nameInput.getText().toString().trim();
            String qtyStr = qtyInput.getText().toString().trim();
            if (name.isEmpty() || qtyStr.isEmpty()) {
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }
            int quantity = Integer.parseInt(qtyStr);
            viewModel.insert(new InventoryItem(name, quantity));
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
