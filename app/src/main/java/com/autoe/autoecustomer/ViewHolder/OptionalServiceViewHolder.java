package com.autoe.autoecustomer.ViewHolder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoe.autoecustomer.Interface.ItemClickListener;
import com.autoe.autoecustomer.R;

public class OptionalServiceViewHolder extends RecyclerView.ViewHolder {
    public TextView txtOptionalServiceLabel,txtOptionalServiceDescription,txtOptionalServicePrice;

    public CheckBox checkBoxImageViewOptionalService;
    public ImageView rupeeSymbolImageViewOptionalService;

    public ItemClickListener itemClickListener;
    public OptionalServiceViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOptionalServicePrice= itemView.findViewById(R.id.price_of_optional_service_textView_optional_service_item);
        txtOptionalServiceDescription= itemView.findViewById(R.id.description_of_optional_service_optional_service_item);
        txtOptionalServiceLabel= itemView.findViewById(R.id.label_of_optional_service_item);
        checkBoxImageViewOptionalService= itemView.findViewById(R.id.check_box_optional_service_item);
        rupeeSymbolImageViewOptionalService = itemView.findViewById(R.id.rupee_symbol_optional_service_item);
    }
}
