package com.autoe.autoecustomer.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoe.autoecustomer.Interface.ItemClickListener;
import com.autoe.autoecustomer.R;


public class CarViewHolder extends RecyclerView.ViewHolder {

    public TextView txtCarLabel, txtCarDesc, txtPriceOfCar;

    public ImageView rupeeIconImageView, radioBoxImageView;

    private ItemClickListener itemClickListener;

    public CarViewHolder(@NonNull View itemView) {
        super(itemView);

        txtCarLabel = itemView.findViewById(R.id.label_of_car_car_item);
        txtCarDesc = itemView.findViewById(R.id.description_of_car_car_item);
        txtPriceOfCar= itemView.findViewById(R.id.price_of_car_textView_car_item);
        rupeeIconImageView = itemView.findViewById(R.id.rupee_symbol_car_item);
        radioBoxImageView = itemView.findViewById(R.id.radio_button_car_item);

        //itemView.setOnClickListener(this);
    }

/*    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);
    }*/
}
