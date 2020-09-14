package com.autoe.autoecustomer;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HelpActivity extends AppCompatActivity {

    private DrawerLayout helpDrawerLayout;
    private Toolbar helpToolbar;
    private ActionBarDrawerToggle helpDrawerToggle;
    private NavigationView navigationView;

    //@param helpDrawerLayout - Drawer Layout to handle navigation drawer
    //@param helpToolbar - Toolbar to handle actions associated with action bar
    //@param helpDrawerToggle - Action Bar Drawer Toggle to handle Drawer toggle in navigation bar
    //@param NavigationView - Navigation View for accessing navigation bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        navigationView = findViewById(R.id.activity_help_nav_view);
        helpDrawerLayout = findViewById(R.id.activity_help_drawer_layout);
        helpToolbar = findViewById(R.id.action_bar_help);
        helpDrawerToggle = new ActionBarDrawerToggle(this,helpDrawerLayout,R.string.open,R.string.close);
        helpDrawerLayout.addDrawerListener(helpDrawerToggle);
        setSupportActionBar(helpToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        helpDrawerToggle.syncState();
        helpDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_text_color));

        navigationView.getBackground().setAlpha(200);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (helpDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (helpDrawerLayout.isDrawerOpen(GravityCompat.START)){
            helpDrawerLayout.closeDrawer(GravityCompat.START);
        }else
            overridePendingTransition(R.anim.activity_back_new_activity,R.anim.activity_back_leaving_activity);
            super.onBackPressed();
    }

    public void Home_from_help(MenuItem item) {
        DrawerLayout drawerLayout = findViewById(R.id.activity_help_drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        Intent intent = new Intent(HelpActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    public void Make_a_booking_from_help(MenuItem item) {
        DrawerLayout drawerLayout = findViewById(R.id.activity_help_drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        Intent intent = new Intent(HelpActivity.this, MakeABooking.class);
        overridePendingTransition(R.anim.activity_switch_new_activity,R.anim.activity_switch_leaving_activity);
        startActivity(intent);
    }
}
