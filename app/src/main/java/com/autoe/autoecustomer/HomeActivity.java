package com.autoe.autoecustomer;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.autoe.autoecustomer.CommonData.Common;
import com.autoe.autoecustomer.Model.User;
import com.google.android.material.navigation.NavigationView;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {



    private DrawerLayout homeDrawerLayout;
    private Toolbar homeToolbar;
    private ActionBarDrawerToggle homeDrawerToggle;
    private NavigationView navigationView;
    private ImageView menuIconHome;
    private LinearLayout makeABookingButton;

    //@param homeDrawerLayout - Drawer Layout to handle navigation drawer
    //@param homeToolbar - Toolbar to handle actions associated with action bar
    //@param homeDrawerToggle - Action Bar Drawer Toggle to handle Drawer toggle in navigation bar
    //@param NavigationView - Navigation View for accessing navigation bar
    //@param menuIconHome - ImageView to handle menuIcon's action
    //@param makeABookingButton - CardView button to open make a new booking activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Paper.init(this);
        navigationView = findViewById(R.id.activity_home_nav_view);
        homeDrawerLayout = findViewById(R.id.activity_home_drawer_layout);
        homeToolbar = findViewById(R.id.navigation_actionbar);
        homeDrawerToggle = new ActionBarDrawerToggle(this,homeDrawerLayout,R.string.open,R.string.close);
        homeDrawerLayout.addDrawerListener(homeDrawerToggle);
        setSupportActionBar(homeToolbar);

        Common.currentUser = Paper.book("user").read("username");

        homeDrawerToggle.syncState();
        homeDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_text_color));

        makeABookingButton = findViewById(R.id.make_a_booking_button_home);
        makeABookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,MakeABooking.class);
                overridePendingTransition(R.anim.activity_switch_new_activity,R.anim.activity_switch_leaving_activity);
                startActivity(intent);
//                Toast.makeText(HomeActivity.this,"Open Make a booking activity..!!",Toast.LENGTH_LONG).show();
            }
        });

        menuIconHome = findViewById(R.id.menu_icon_home);
        menuIconHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeDrawerLayout.openDrawer(GravityCompat.START);
            }
        });


        navigationView.getBackground().setAlpha(200);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (homeDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if (homeDrawerLayout.isDrawerOpen(GravityCompat.START)){
            homeDrawerLayout.closeDrawer(GravityCompat.START);
        }else
            finishAffinity();
    }

    public void Help_from_home(MenuItem item) {
        homeDrawerLayout.closeDrawer(GravityCompat.START);
        Intent intent = new Intent(HomeActivity.this,HelpActivity.class);
        overridePendingTransition(R.anim.activity_switch_new_activity,R.anim.activity_switch_leaving_activity);
        startActivity(intent);
    }

    public void Make_a_booking_from_home(MenuItem item) {
        DrawerLayout drawerLayout = findViewById(R.id.activity_home_drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        Intent intent = new Intent(HomeActivity.this, MakeABooking.class);
        overridePendingTransition(R.anim.activity_switch_new_activity,R.anim.activity_switch_leaving_activity);
        startActivity(intent);
    }

    public void Account_Activity_from_home(MenuItem item) {
        DrawerLayout drawerLayout = findViewById(R.id.activity_home_drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
        overridePendingTransition(R.anim.activity_switch_new_activity,R.anim.activity_switch_leaving_activity);
        startActivity(intent);
    }
}

