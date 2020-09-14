package com.autoe.autoecustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BookingActivity extends AppCompatActivity {
    private ImageView back_button_booking,edit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        InitializeVariables();

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingActivity.this, MakeABooking.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_down_new_activity,R.anim.activity_down_leaving_activty);
            }
        });

        back_button_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingActivity.this, SelectYourPackage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_back_new_activity,R.anim.activity_back_leaving_activity);
            }
        });
    }

    private void InitializeVariables() {
        back_button_booking = findViewById(R.id.back_button_booking);
        edit_button = findViewById(R.id.edit_imageView_booking_summary);
    }
}
