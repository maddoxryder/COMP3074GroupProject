package com.example.comp3074uiwireframe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private List<InventoryItem> items = new ArrayList<>();
    private OnItemQuantityChangeListener listener;

    public interface OnItemQuantityChangeListener {
        void onQuantityChanged(InventoryItem item);
    }

    public void setOnItemQuantityChangeListener(OnItemQuantityChangeListener listener) {
        this.listener = listener;
    }

    public void setItems(List<InventoryItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inventory_row, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        InventoryItem item = items.get(position);
        holder.tvName.setText(item.getName());
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        holder.tvLowStock.setVisibility(item.getQuantity() < 10 ? View.VISIBLE : View.GONE);

        holder.btnIncrease.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
            if (listener != null) listener.onQuantityChanged(item);
            holder.tvLowStock.setVisibility(item.getQuantity() < 10 ? View.VISIBLE : View.GONE);
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (item.getQuantity() > 0) {
                item.setQuantity(item.getQuantity() - 1);
                holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
                if (listener != null) listener.onQuantityChanged(item);
                holder.tvLowStock.setVisibility(item.getQuantity() < 10 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQuantity, tvLowStock;
        Button btnIncrease, btnDecrease;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvLowStock = itemView.findViewById(R.id.tvLowStock);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }
}
