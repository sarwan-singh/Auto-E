package com.autoe.autoecustomer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoe.autoecustomer.Model.CarItem;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private List<CarItem> carItems;
    private Context context;

    public CarAdapter(List<CarItem> carItems, Context context){
        this.carItems = carItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarItem carItem = carItems.get(position);
        holder.descriptionTextView.setText(carItem.getDescription_of_car());
        holder.labelTextView.setText(carItem.getLabel_of_car());
        holder.priceTextView.setText(carItem.getPrice_of_car());
    }

    @Override
    public int getItemCount() {
        return carItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView priceTextView;
        private TextView descriptionTextView;
        private TextView labelTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            priceTextView = itemView.findViewById(R.id.price_of_car_textView_car_item);
            labelTextView = itemView.findViewById(R.id.label_of_car_car_item);
            descriptionTextView = itemView.findViewById(R.id.description_of_car_car_item);
        }
    }
}
