package com.autoe.autoecustomer.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoe.autoecustomer.Interface.ItemClickListener;
import com.autoe.autoecustomer.R;

public class MainServiceViewHolder extends RecyclerView.ViewHolder {
    public TextView txtMainServiceLabel,txtMainServiceDescription,txtMainServicePrice,txtMainServiceReadMore;

    public ImageView radioBoxImageViewMainService,rupeeSymbolImageViewMainService;

    public ItemClickListener itemClickListener;

    public MainServiceViewHolder(@NonNull View itemView) {
        super(itemView);
        txtMainServicePrice = itemView.findViewById(R.id.price_of_service_textView_main_service_item);
        txtMainServiceDescription = itemView.findViewById(R.id.description_of_service_main_service_item);
        txtMainServiceLabel = itemView.findViewById(R.id.label_of_service_main_service_item);
        txtMainServiceReadMore = itemView.findViewById(R.id.read_more_main_service_item);
        radioBoxImageViewMainService = itemView.findViewById(R.id.radio_button_main_service_item);
        rupeeSymbolImageViewMainService = itemView.findViewById(R.id.rupee_symbol_main_service_item);
    }
}
