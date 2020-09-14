package com.autoe.autoecustomer.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class CarOfUser {
    private DatabaseReference table_car_type;

    private String type;
    private String desc;
    private String label;


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String price;

    public CarOfUser() {
    }

    public CarOfUser(String type, String desc, String label) {

        table_car_type = FirebaseDatabase.getInstance().getReference("CarType");

        table_car_type.child(type).child("priceForType").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("address","abcdAdresscar"+ Objects.requireNonNull(dataSnapshot.getValue()).toString());
                price = Objects.requireNonNull(dataSnapshot.getValue()).toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        this.type = type;
        this.desc = desc;
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
