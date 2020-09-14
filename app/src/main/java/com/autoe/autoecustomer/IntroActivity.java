package com.autoe.autoecustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private IntroManager introManager;
    private TextView[] dots;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout dotsLayout;
    private int[] layouts;

    //@param viewPager - viewPager between intro screens
    //@param introManager - check whether user is first time user or not
    //@param dots - dots indicating which screen is currently open.
    //@param viewPagerAdapter - adapter for viewPager to show the intro screens as a fragment
    //static slideshow
    //@param dotsLayout - layout to display dots
    //@param layouts - layout to display intro screens

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currUser= FirebaseAuth.getInstance().getCurrentUser();

        introManager = new IntroManager(this);
        if (currUser!=null){
            introManager.setFirst(true);
            Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        introManager.setFirst(true);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.intro_Page_Viewer);
        dotsLayout = findViewById(R.id.LayoutDots);
        layouts = new int[]{R.layout.intro_screen_1,R.layout.intro_screen_2,R.layout.intro_screen_3,
                R.layout.intro_screen_4};
        addBottomDots(0);
        changeStatusBarColor();
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageTransformer(false,new FadePageTransformer());
        viewPager.addOnPageChangeListener(viewListener);

    }

    private void addBottomDots(int position){

        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dot_active);
        int[] colorInactive = getResources().getIntArray(R.array.dot_inactive);
        dotsLayout.removeAllViews();
        for (int i=0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226; "));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[position]);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length>0)
            dots[position].setTextColor(colorActive[position]);
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position==3){
                dotsLayout.removeAllViews();
            }else {
            addBottomDots(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private void changeStatusBarColor(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    public void a(View view) {
        Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void alreadyHaveAAccount(View view) {


        Intent intent = new Intent(IntroActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }

    public void checkAvailabilityButton(View view) {

        Toast.makeText(IntroActivity.this,"Open Check Availability Activity..!!",Toast.LENGTH_LONG).show();

    }


    public class ViewPagerAdapter extends PagerAdapter{

        private LayoutInflater layoutInflater;

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position],container,false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View)object;
            container.removeView(view);
        }
    }
    public class FadePageTransformer implements ViewPager.PageTransformer {
        public void transformPage(View view, float position) {

            if (position < -1 || position > 1) {
                view.setAlpha(0);
            }
            else if (position <= 0 || position <= 1) {
                float alpha = (position <= 0) ? position + 1 : 1 - position;
                view.setAlpha(alpha);
            }
            else if (position == 0) {
                view.setAlpha(1);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}

