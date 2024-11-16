package com.example.commonproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private List<String> texts;
    List<String> links;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ItemAdapter(Context context, List<String> texts, List<String> links, OnItemClickListener listener) {
        this.context = context;
        this.texts = texts;
        this.links = links;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String text = texts.get(position);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CheckBox checkBox;
        TextView textView;

        ItemViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            textView = itemView.findViewById(R.id.text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (listener != null && position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position);
            }
        }
    }
}