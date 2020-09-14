package com.autoe.autoecustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.autoe.autoecustomer.CommonData.Common;
import com.autoe.autoecustomer.Model.CarOfUser;
import com.autoe.autoecustomer.Model.CarType;
import com.autoe.autoecustomer.ViewHolder.CarViewHolder;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SelectACar extends AppCompatActivity {

    private ImageView back_button_select_a_car;

    private Button add_a_car_select_a_car;

    private CardView layout_car_exist_cardView,add_a_car_button_select_a_car_car_exists,next_button_select_a_car;

    private ScrollView layout_car_exists_scrollView;

    private FirebaseRecyclerAdapter<CarOfUser,CarViewHolder> adapter;

    private RecyclerView.Adapter carAdapter;

    private RecyclerView carRecyclerView;

    private LottieAnimationView animation_no_car,loading_car_animation;

    private TextView no_car_text,next_textView_select_a_car;

    private FirebaseUser firebaseUser;

    public  Thread thread;
    // private List<CarItem> carItems;

    private int selectedPositionOfCar;

    private DatabaseReference table_car;

    FirebaseRecyclerOptions<CarOfUser> options;

    private String selectedCarType;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_acar);

        initializeVariables();

        selectedCarType = Common.selectedCarType;
        selectedPositionOfCar = -1;

        table_car = FirebaseDatabase.getInstance().getReference("Cars").child(firebaseUser.getUid());
        DatabaseReference table_car_type = FirebaseDatabase.getInstance().getReference("CarType");
        options= new FirebaseRecyclerOptions.Builder<CarOfUser>()
                .setQuery(table_car, CarOfUser.class).build();

        next_button_select_a_car.setVisibility(View.GONE);

        carRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FirebaseRecyclerAdapter<CarOfUser,CarViewHolder>(options) {

            @NonNull
            @Override
            public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);

                return new CarViewHolder(itemView);

            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                checkStatus();
            }

            @Override
            protected void onBindViewHolder(@NonNull final CarViewHolder carViewHolder, int i, @NonNull final CarOfUser carOfUser) {


                carViewHolder.txtCarLabel.setText(carOfUser.getLabel());
                carViewHolder.txtCarDesc.setText(carOfUser.getDesc());
                carViewHolder.txtPriceOfCar.setText(carOfUser.getPrice());

                if (selectedPositionOfCar == i) {
                    carViewHolder.txtPriceOfCar.setTextColor(getResources().getColor(R.color.colorSecondary));
                    carViewHolder.txtCarLabel.setTextColor(getResources().getColor(R.color.color_selected_car));
                    carViewHolder.rupeeIconImageView.setImageResource(R.drawable.rupee_icon);
                    carViewHolder.radioBoxImageView.setImageResource(R.drawable.selected_image_view_checked);
                    selectedCarType = carOfUser.getType();
/*
                    Common.selectedCarType = selectedCarType;*/

                    Common.selectedCarType = selectedCarType;
                    Common.currentBooking.setCarType(selectedCarType);

                    Log.d("CarType",selectedCarType);
                } else {
                    carViewHolder.txtPriceOfCar.setTextColor(getResources().getColor(R.color.color_not_selected_car));
                    carViewHolder.txtCarLabel.setTextColor(getResources().getColor(R.color.color_not_selected_car));
                    carViewHolder.rupeeIconImageView.setImageResource(R.drawable.rupee_icon_not_selected);
                    carViewHolder.radioBoxImageView.setImageResource(R.drawable.unselected_image_view_unchecked);

                    selectedCarType = "";
                    Common.selectedCarType = selectedCarType;
                }


                carViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedPositionOfCar >= 0)
                            notifyItemChanged(selectedPositionOfCar);
                        if (selectedPositionOfCar == carViewHolder.getAdapterPosition()){
                            selectedPositionOfCar = -1;
                            notifyItemChanged(selectedPositionOfCar);
                        }else {
                            selectedPositionOfCar = carViewHolder.getAdapterPosition();
                            notifyItemChanged(selectedPositionOfCar);
                        }
                    }
                });

                if (selectedPositionOfCar !=-1){
                    next_textView_select_a_car.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                }else {
                    next_textView_select_a_car.setBackgroundColor(getResources().getColor(R.color.color_not_selected_car));
                }
            }
        };



        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.notifyItemRemoved(viewHolder.getLayoutPosition());
                table_car.removeValue();
                Log.d("carID",String.valueOf(viewHolder.getLayoutPosition()));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkStatus();
                    }
                },3000);
                checkStatus();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(carRecyclerView);


        back_button_select_a_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMakeABooking();
            }
        });

        add_a_car_select_a_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addACarFromSelectACar();
            }
        });

        add_a_car_button_select_a_car_car_exists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addACarFromSelectACar();
            }
        });

        thread = Thread.currentThread();


        loadMenu();

        next_button_select_a_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPositionOfCar==-1){
                    Toast.makeText(getApplicationContext(),"Please select a car to continue",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(SelectACar.this,SelectYourPackage.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_switch_new_activity,R.anim.activity_switch_leaving_activity);
                }
            }
        });

    }

    private void checkStatus() {

        Log.v("number of items: ","abcdabcdabcd"+ adapter.getItemCount());

        if (adapter.getItemCount()!=0){

            animation_no_car.setVisibility(View.INVISIBLE);

            add_a_car_select_a_car.setVisibility(View.INVISIBLE);

            no_car_text.setVisibility(View.INVISIBLE);

            layout_car_exists_scrollView.setVisibility(View.VISIBLE);

            layout_car_exist_cardView.setVisibility(View.VISIBLE);

            next_button_select_a_car.setVisibility(View.VISIBLE);

            if (adapter.getItemCount()>=5){
                add_a_car_button_select_a_car_car_exists.setVisibility(View.INVISIBLE);
            }else {
                add_a_car_button_select_a_car_car_exists.setVisibility(View.VISIBLE);
            }

        }else {

            animation_no_car.setVisibility(View.VISIBLE);

            add_a_car_select_a_car.setVisibility(View.VISIBLE);

            no_car_text.setVisibility(View.VISIBLE);

            layout_car_exists_scrollView.setVisibility(View.INVISIBLE);

            layout_car_exist_cardView.setVisibility(View.INVISIBLE);

            next_button_select_a_car.setVisibility(View.GONE);

            add_a_car_button_select_a_car_car_exists.setVisibility(View.INVISIBLE);

        }
        loading_car_animation.setVisibility(View.GONE);



        Objects.requireNonNull(carRecyclerView.getAdapter()).notifyDataSetChanged();
    }

    private void addACarFromSelectACar() {
        Log.v("number of items: ","abcdabcdabcd"+ adapter.getItemCount());

        Intent intent = new Intent(SelectACar.this, AddACar.class);
        if (adapter.getItemCount() == 0) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SelectACar.this, add_a_car_select_a_car, ViewCompat.getTransitionName(add_a_car_select_a_car));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
            overridePendingTransition(R.anim.activity_switch_new_activity, R.anim.activity_switch_leaving_activity);
        }
    }

    private void initializeVariables() {
        back_button_select_a_car = findViewById(R.id.back_button_select_a_car);

        add_a_car_select_a_car = findViewById(R.id.add_a_car_select_a_car);

        animation_no_car = findViewById(R.id.no_car_animation);

        loading_car_animation = findViewById(R.id.loading_car_animation);

        layout_car_exists_scrollView = findViewById(R.id.layout_car_exists_scroll_view);

        layout_car_exist_cardView = findViewById(R.id.layout_car_exist_select_a_car);

        next_button_select_a_car = findViewById(R.id.next_button_select_a_car);

        carRecyclerView = findViewById(R.id.recycler_view_car);

        no_car_text = findViewById(R.id.no_car_text_select_a_car);

        next_textView_select_a_car = findViewById(R.id.next_textView_select_a_car);

        add_a_car_button_select_a_car_car_exists = findViewById(R.id.add_a_car_button_select_a_car_with_car);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public void onBackPressed() {
        backToMakeABooking();
    }

    public void backToMakeABooking(){
        Intent intent = new Intent(SelectACar.this,MakeABooking.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_back_new_activity,R.anim.activity_back_leaving_activity);
    }

    private void loadMenu() {

        options= new FirebaseRecyclerOptions.Builder<CarOfUser>()
                .setQuery(table_car,CarOfUser.class).build();
        carRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        carAdapter= new FirebaseRecyclerAdapter<CarOfUser, CarViewHolder>(options) {
            @NonNull
            @Override
            public CarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.car_item, viewGroup, false);

                return new CarViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull CarViewHolder holder, int position, @NonNull final CarOfUser model) {

            }

        };


        adapter.startListening();
        carRecyclerView.setAdapter(adapter);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_recycler_view_car_and_location_fall_down);

//        swipeRefreshLayout.setRefreshing(false);
        carRecyclerView.setLayoutAnimation(controller);
        Objects.requireNonNull(carRecyclerView.getAdapter()).notifyDataSetChanged();
        carRecyclerView.scheduleLayoutAnimation();



        Log.v("number of items: ","abcdabcdabcdbefore"+ adapter.getItemCount());

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                checkStatus();

                Log.v("number of items: ","abcdabcdabcdafter"+ adapter.getItemCount());

            }
        },3200);


    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
        Log.v("number of items: ","abcdabcdabcd"+ adapter.getItemCount());
    }



}
