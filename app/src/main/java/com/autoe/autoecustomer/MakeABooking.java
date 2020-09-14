package com.autoe.autoecustomer;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.autoe.autoecustomer.CommonData.Common;
import com.autoe.autoecustomer.Model.Booking;
import com.autoe.autoecustomer.Model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.paperdb.Book;

public class MakeABooking extends AppCompatActivity {

    private View layout_exist_relative_layout;

    private CardView add_a_location_cardView,next_button_select_a_location;

    private ImageView back_button;
    ImageView no_location_image;

    private Button add_a_location;

    private RecyclerView locationRecyclerView;
    public static RecyclerView.Adapter locationAdapter;

    private List<Location> locationItems;

    public static int isSelected=0;


    //@param back_button - ImageView to send activity back

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_abooking);

        setTitle("Activity Make A Booking");

        initializeVariables();

        if(getIntent().getExtras()!=null)
        {
            String yesNo = getIntent().getExtras().getString("isFromAccount");

            if(yesNo.equals("yes"))
            {
                next_button_select_a_location.setVisibility(View.GONE);
            }
        }

        Booking booking = new Booking();

        booking.setEmailOfUser(Common.currentUser.getEmail());
        booking.setExecutiveAssigned("");
        booking.setNameOfUser(Common.currentUser.getName());
        booking.setMobileOfUser(Common.currentUser.getMobile());
        Common.currentBooking = booking;

        locationRecyclerView.setHasFixedSize(true);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        locationItems = new ArrayList<>();

       /* LocationItem item1 = new LocationItem("PAPA","beta",R.drawable.onboarding_page1);
        LocationItem item2 = new LocationItem("PAPA","204 a, hostel h5 , amity university rajasthan, ricco industrial area,kant kalwar, jaipur, rajasthan , 303002",R.drawable.onboarding_page3);
        LocationItem item3 = new LocationItem("PAPA","204 a, hostel h5 , amity university rajasthan, ricco industrial area,kant kalwar, jaipur, rajasthan , 303002",R.drawable.onboarding_page2);

        locationItems.add(item1);
        locationItems.add(item2);
        locationItems.add(item3);*/

//        locationItems.remove(1);
        /*locationItems.remove(0);
        locationAdapter = new LocationAdapter(locationItems,this);

        locationRecyclerView.setAdapter(locationAdapter);*/
        populateRecyclerView();

        checkStatus();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackButton();
            }
        });

        next_button_select_a_location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(LocationAdapter.isSelectedLocation==1)
                    selectACar();
                else{
                    Toast.makeText(MakeABooking.this, "Please select a location", Toast.LENGTH_SHORT).show();
                }
            }
        });

        add_a_location_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddALocationWithLocation();
            }
        });

        add_a_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddALocation();
            }
        });

    }


    private void populateRecyclerView()
    {
        locationItems = new ArrayList<>();

        if(!Common.currentUser.getHomeAddress().getLatitude().equals(""))
        {
            locationItems.add(Common.currentUser.getHomeAddress());
        }
        if(!Common.currentUser.getOfficeAddress().getLatitude().equals(""))
        {
            locationItems.add(Common.currentUser.getOfficeAddress());
        }
        if(!Common.currentUser.getOtherAddress().getLatitude().equals(""))
        {
            locationItems.add(Common.currentUser.getOtherAddress());
        }
        locationAdapter = new LocationAdapter(locationItems,this);

        locationRecyclerView.setAdapter(locationAdapter);

        Objects.requireNonNull(locationRecyclerView.getAdapter()).notifyDataSetChanged();
        locationRecyclerView.scheduleLayoutAnimation();
    }


    private void initializeVariables() {

        back_button = findViewById(R.id.back_button_make_a_booking);

        add_a_location = findViewById(R.id.add_a_location_button_make_a_booking);

        add_a_location_cardView = findViewById(R.id.add_a_location_button_make_a_booking_with_location);

        no_location_image = findViewById(R.id.no_location_image);

        layout_exist_relative_layout = findViewById(R.id.layout_location_exist_select_a_location);

        next_button_select_a_location = findViewById(R.id.next_button_select_a_location);

        locationRecyclerView = findViewById(R.id.recycler_view_location);
    }


    public void checkStatus() {
        if (locationItems.size()==0){
            add_a_location_cardView.setVisibility(View.GONE);
            locationRecyclerView.setVisibility(View.GONE);
            add_a_location.setVisibility(View.VISIBLE);
            no_location_image.setVisibility(View.VISIBLE);
            layout_exist_relative_layout.setVisibility(View.GONE);
        }else {
            add_a_location_cardView.setVisibility(View.VISIBLE);
            locationRecyclerView.setVisibility(View.VISIBLE);
            add_a_location.setVisibility(View.GONE);
            no_location_image.setVisibility(View.GONE);
            layout_exist_relative_layout.setVisibility(View.VISIBLE);
            if (locationItems.size()==3){
                add_a_location_cardView.setVisibility(View.GONE);
            }
        }

        /*if(LocationAdapter.isSelectedLocation == 0)
        {
            Toast.makeText(this, "Select", Toast.LENGTH_SHORT).show();
        }
        else if(LocationAdapter.isSelectedLocation == 1)
        {
            Toast.makeText(this, "Not Selected", Toast.LENGTH_SHORT).show();
        }*/

    }

    private void selectACar() {
        Intent intent = new Intent(MakeABooking.this,SelectACar.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MakeABooking.this,next_button_select_a_location,ViewCompat.getTransitionName(next_button_select_a_location));
        startActivity(intent,options.toBundle());
        overridePendingTransition(R.anim.activity_switch_new_activity,R.anim.activity_switch_leaving_activity);
    }

    public void AddALocationWithLocation(){
        Intent intent = new Intent(MakeABooking.this,AddALocation.class);
//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MakeABooking.this,add_a_location_cardView, ViewCompat.getTransitionName(add_a_location));
        startActivity(intent);
        overridePendingTransition(R.anim.activity_switch_new_activity,R.anim.activity_switch_leaving_activity);
    }


    public void AddALocation(){
        Intent intent = new Intent(MakeABooking.this,AddALocation.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MakeABooking.this,add_a_location, ViewCompat.getTransitionName(add_a_location));
        startActivity(intent,options.toBundle());
        overridePendingTransition(R.anim.activity_switch_new_activity,R.anim.activity_switch_leaving_activity);
    }

    public void BackButton(){
        Intent intent = new Intent(MakeABooking.this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_back_new_activity,R.anim.activity_back_leaving_activity);
    }

    @Override
    public void onBackPressed() {
        BackButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateRecyclerView();
    }
}
