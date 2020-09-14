package com.autoe.autoecustomer;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoe.autoecustomer.Model.Location;
import com.bumptech.glide.Glide;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<Location> locationItems;
    private Context context;

    public int selectedPositionOfCar =-1;

    public static int isSelectedLocation = 0;

    public LocationAdapter(List<Location> locationItems, Context context) {
        this.locationItems= locationItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Location locationItem = locationItems.get(i);

        DisplayMetrics displayMetrics;
        displayMetrics= context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        //holder.mapImage.setImageResource(locationItem.getMapResource());
        /*Glide.with(context).load("https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyCQbVdepVtGA01KACwE_YbmdsSfiLDSe_4" +
                "&center="+locationItem.getLatitude()+","+locationItem.getLongitude()+"&markers=color:red%7Clabel:S%7C" +
                locationItem.getLatitude()+","+locationItem.getLongitude()+"&zoom=10&format=png&maptype=roadmap&style=element:geometry%7Ccolor:0x212121&style=element:labels.icon%7Cvisibility:off&style=element:labels.text.fill%7Ccolor:0x757575&style=element:labels.text.stroke%7Ccolor:0x212121&style=feature:administrative%7Celement:geometry%7Ccolor:0x757575&style=feature:administrative.country%7Celement:labels.text.fill%7Ccolor:0x9e9e9e&style=feature:administrative.land_parcel%7Cvisibility:off&style=feature:administrative.locality%7Celement:labels.text.fill%7Ccolor:0xbdbdbd&style=feature:poi%7Celement:labels.text.fill%7Ccolor:0x757575&style=feature:poi.park%7Celement:geometry%7Ccolor:0x181818&style=feature:poi.park%7Celement:labels.text.fill%7Ccolor:0x616161&style=feature:poi.park%7Celement:labels.text.stroke%7Ccolor:0x1b1b1b&style=feature:road%7Celement:geometry.fill%7Ccolor:0x2c2c2c&style=feature:road%7Celement:labels.text.fill%7Ccolor:0x8a8a8a&style=feature:road.arterial%7Celement:geometry%7Ccolor:0x373737&style=feature:road.highway%7Celement:geometry%7Ccolor:0x3c3c3c&style=feature:road.highway.controlled_access%7Celement:geometry%7Ccolor:0x4e4e4e&style=feature:road.local%7Celement:labels.text.fill%7Ccolor:0x616161&style=feature:transit%7Celement:labels.text.fill%7Ccolor:0x757575&style=feature:water%7Celement:geometry%7Ccolor:0x000000&style=feature:water%7Celement:labels.text.fill%7Ccolor:0x3d3d3d&size="+String.valueOf(width)+"x360").into(holder.mapImage);
        *//* Glide.with(context).load("https://maps.googleapis.com/maps/api/staticmap?zoom=15&size="+width+"x150" +
                "&maptype=roadmap&markers=color:red%7Clabel:S%7C" +
                locationItem.getLatitude()+","+
                locationItem.getLongitude()+"&key=AIzaSyCQbVdepVtGA01KACwE_YbmdsSfiLDSe_4").into(holder.mapImage);*/


        holder.addressTextView.setText(locationItem.getDescOfLocation());
        holder.labelTextView.setText(locationItem.getLabelOfLocation());

        if (selectedPositionOfCar == i) {
            holder.addressTextView.setTextColor(context.getResources().getColor(R.color.colorSecondary));
            holder.labelTextView.setTextColor(context.getResources().getColor(R.color.color_selected_car));
            //holder.mapImage.rupeeIconImageView.setImageResource(R.drawable.rupee_icon);
            holder.radioButtonChecked.setImageResource(R.drawable.selected_image_view_checked);
            Glide.with(context).load("https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyCQbVdepVtGA01KACwE_YbmdsSfiLDSe_4" +
                    "&center="+locationItem.getLatitude()+","+locationItem.getLongitude()+"&markers=color:red%7Clabel:S%7C" +
                    locationItem.getLatitude()+","+locationItem.getLongitude()+"&zoom=12&format=png&maptype=roadmap&style=" +
                    "element:geometry%7Ccolor:0x212121&style=element:labels.icon%7Cvisibility:off&style=element:labels.te" +
                    "xt.fill%7Ccolor:0x757575&style=element:labels.text.stroke%7Ccolor:0x212121&style=feature:administrat" +
                    "ive%7Celement:geometry%7Ccolor:0x757575&style=feature:administrative.country%7Celement:labels.text.fi" +
                    "ll%7Ccolor:0x9e9e9e&style=feature:administrative.land_parcel%7Cvisibility:off&style=feature:administra" +
                    "tive.locality%7Celement:labels.text.fill%7Ccolor:0xbdbdbd&style=feature:poi%7Celement:labels.text.fil" +
                    "l%7Ccolor:0x757575&style=feature:poi.park%7Celement:geometry%7Ccolor:0x181818&style=feature:poi.park%" +
                    "7Celement:labels.text.fill%7Ccolor:0x616161&style=feature:poi.park%7Celement:labels.text.stroke%7" +
                    "Ccolor:0x1b1b1b&style=feature:road%7Celement:geometry.fill%7Ccolor:0x2c2c2c&style=feature:road%7" +
                    "Celement:labels.text.fill%7Ccolor:0x8a8a8a&style=feature:road.arterial%7Celement:geometry%7Ccolo" +
                    "r:0x373737&style=feature:road.highway%7Celement:geometry%7Ccolor:0x3c3c3c&style=feature:road.high" +
                    "way.controlled_access%7Celement:geometry%7Ccolor:0x4e4e4e&style=feature:road.local%7Celement:lab" +
                    "els.text.fill%7Ccolor:0x616161&style=feature:transit%7Celement:labels.text.fill%7Ccolor:0x757575" +
                    "&style=feature:water%7Celement:geometry%7Ccolor:0x000000&style=feature:water%7Celement:labels.te" +
                    "xt.fill%7Ccolor:0x3d3d3d&size="+String.valueOf(width)+"x360").into(holder.mapImage);
          //  MakeABooking.next_button_select_a_location.setBackgroundColor(context.getResources().getColor(R.color.color_selected_car));

            //MakeABooking.add_a_location_cardView.setBackgroundColor(context.getResources().getColor(R.color.color_not_selected_car));
            isSelectedLocation = 1;
        } else {
            holder.addressTextView.setTextColor(context.getResources().getColor(R.color.color_not_selected_car));
            holder.labelTextView.setTextColor(context.getResources().getColor(R.color.color_not_selected_car));
            //carViewHolder.rupeeIconImageView.setImageResource(R.drawable.rupee_icon_not_selected);
            holder.radioButtonChecked.setImageResource(R.drawable.unselected_image_view_unchecked);
            Glide.with(context).load("https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyCQbVdepVtGA01KACwE_YbmdsSfiLDSe_4" +
                    "&center="+locationItem.getLatitude()+","+locationItem.getLongitude()+"&markers=color:red%7Clabel:S%7C" +
                    locationItem.getLatitude()+","+locationItem.getLongitude()+"&zoom=12&format=png&maptype=roadmap&style=element:geometry" +
                    "%7Ccolor:0xebe3cd&style=element:labels.text.fill%7Ccolor:0x523735&style=element:labels.text.stroke%7Ccolor" +
                    ":0xf5f1e6&style=feature:administrative%7Celement:geometry.stroke%7Ccolor:0xc9b2a6&style=feature:administrat" +
                    "ive.land_parcel%7Celement:geometry.stroke%7Ccolor:0xdcd2be&style=feature:administrative.land_parcel%7Celemen" +
                    "t:labels.text.fill%7Ccolor:0xae9e90&style=feature:landscape.natural%7Celement:geometry%7Ccolor:0xdfd2ae&sty" +
                    "le=feature:poi%7Celement:geometry%7Ccolor:0xdfd2ae&style=feature:poi%7Celement:labels.text.fill%7Ccolor:0x9" +
                    "3817c&style=feature:poi.park%7Celement:geometry.fill%7Ccolor:0xa5b076&style=feature:poi.park%7Celement:lab" +
                    "els.text.fill%7Ccolor:0x447530&style=feature:road%7Celement:geometry%7Ccolor:0xf5f1e6&style=feature:road.a" +
                    "rterial%7Celement:geometry%7Ccolor:0xfdfcf8&style=feature:road.highway%7Celement:geometry%7Ccolor:0xf8c96" +
                    "7&style=feature:road.highway%7Celement:geometry.stroke%7Ccolor:0xe9bc62&style=feature:road.highway.contr" +
                    "olled_access%7Celement:geometry%7Ccolor:0xe98d58&style=feature:road.highway.controlled_access%7Celement:" +
                    "geometry.stroke%7Ccolor:0xdb8555&style=feature:road.local%7Celement:labels.text.fill%7Ccolor:0x806b63&st" +
                    "yle=feature:transit.line%7Celement:geometry%7Ccolor:0xdfd2ae&style=feature:transit.line%7Celement:labels" +
                    ".text.fill%7Ccolor:0x8f7d77&style=feature:transit.line%7Celement:labels.text.stroke%7Ccolor:0xebe3cd&sty" +
                    "le=feature:transit.station%7Celement:geometry%7Ccolor:0xdfd2ae&style=feature:water%7Celement:geometry.f" +
                    "ill%7Ccolor:0xb9d3c2&style=feature:water%7Celement:labels.text.fill%7Ccolor:0x92998d&" +
                    "size="+String.valueOf(width)+"x360").into(holder.mapImage);

            isSelectedLocation = 0;



           // MakeABooking.add_a_location_cardView.setBackgroundColor(context.getResources().getColor(R.color.color_selected_car ));
            //next_button_select_a_location.setBackgroundColor(context.getResources().getColor(R.color.color_not_selected_car));

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPositionOfCar >= 0) {
                    isSelectedLocation = 1;
                    notifyItemChanged(selectedPositionOfCar);

                   // next_button_select_a_location.setBackgroundColor(context.getResources().getColor(R.color.color_selected_car));
                   // MakeABooking.next_button_select_a_location.setBackgroundColor(context.getResources().getColor(R.color.color_selected_car));
                }
                if (selectedPositionOfCar == holder.getAdapterPosition()){
                    selectedPositionOfCar = -1;
                    isSelectedLocation = 0;
                    notifyItemChanged(selectedPositionOfCar);

                  // MakeABooking.next_button_select_a_location.setBackgroundColor(context.getResources().getColor(R.color.color_not_selected_car));
                }else {
                    selectedPositionOfCar = holder.getAdapterPosition();
                    isSelectedLocation = 1;
                    notifyItemChanged(selectedPositionOfCar);

                   // MakeABooking.next_button_select_a_location.setBackgroundColor(context.getResources().getColor(R.color.color_selected_car));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return locationItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mapImage;
        private TextView labelTextView;
        private TextView addressTextView;
        private ImageView radioButtonChecked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mapImage = itemView.findViewById(R.id.map_location_item);
            labelTextView = itemView.findViewById(R.id.label_of_location_location_item);
            addressTextView = itemView.findViewById(R.id.address_location_item);
            radioButtonChecked = itemView.findViewById(R.id.radio_button_location_item);
        }
    }
}
