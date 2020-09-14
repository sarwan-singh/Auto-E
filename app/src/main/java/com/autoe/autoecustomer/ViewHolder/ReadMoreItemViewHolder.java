package com.autoe.autoecustomer.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoe.autoecustomer.R;

public class ReadMoreItemViewHolder extends RecyclerView.ViewHolder {
    public TextView value;


    public ReadMoreItemViewHolder(@NonNull View itemView) {
        super(itemView);

        value = itemView.findViewById(R.id.textView_read_more_item);
    }
}
