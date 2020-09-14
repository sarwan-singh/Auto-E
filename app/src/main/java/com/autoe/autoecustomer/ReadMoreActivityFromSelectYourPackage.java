package com.autoe.autoecustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.autoe.autoecustomer.Model.MainService;
import com.autoe.autoecustomer.Model.ReadMoreItem;
import com.autoe.autoecustomer.ViewHolder.MainServiceViewHolder;
import com.autoe.autoecustomer.ViewHolder.OptionalServiceViewHolder;
import com.autoe.autoecustomer.ViewHolder.ReadMoreItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static android.util.Pair.create;

public class ReadMoreActivityFromSelectYourPackage extends AppCompatActivity {

    private String priceReceived, labelReceived, descriptionMain;

    private TextView price_textView_read_more_select_your_package, label_textView_read_more_select_your_package, description_textView_read_more_select_your_package;

    private RecyclerView readMoreItemRecyclerView;

    private FirebaseRecyclerOptions<ReadMoreItem> options;

    private DatabaseReference table_readMore_item;
    private FirebaseRecyclerAdapter<ReadMoreItem, ReadMoreItemViewHolder> read_more_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_more_from_select_your_package);

        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                priceReceived = null;
                labelReceived = null;
                descriptionMain = null;
            }else {
                labelReceived = extras.getString("label value select your value read more");
                priceReceived = extras.getString("price value select your value read more");
                descriptionMain = extras.getString("description value select your value read more");
            }
        }else{
            labelReceived = (String) savedInstanceState.getSerializable("label value select your value read more");
            priceReceived = (String) savedInstanceState.getSerializable("price value select your value read more");
            descriptionMain = (String) savedInstanceState.getSerializable("description value select your value read more");
        }

        price_textView_read_more_select_your_package = findViewById(R.id.price_read_more_activity);

        label_textView_read_more_select_your_package = findViewById(R.id.label_read_more_activity);

        description_textView_read_more_select_your_package = findViewById(R.id.main_description_of_service);

        price_textView_read_more_select_your_package.setText(priceReceived);

        label_textView_read_more_select_your_package.setText(labelReceived);

        description_textView_read_more_select_your_package.setText(descriptionMain);

        readMoreItemRecyclerView = findViewById(R.id.recycler_view_read_more_activity);

        table_readMore_item = FirebaseDatabase.getInstance().getReference().child("Services").child("Description nodes").child(labelReceived);

        options = new FirebaseRecyclerOptions.Builder<ReadMoreItem>()
                .setQuery(table_readMore_item, ReadMoreItem.class).build();

        readMoreItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        read_more_adapter= new FirebaseRecyclerAdapter<ReadMoreItem,ReadMoreItemViewHolder>(options) {

            @NonNull
            @Override
            public ReadMoreItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.read_more_item, parent, false);

                return new ReadMoreItemViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull ReadMoreItemViewHolder readMoreItemViewHolder, int i, @NonNull ReadMoreItem readMoreItem) {
                readMoreItemViewHolder.value.setText(readMoreItem.getValueOfItem());
            }
        };

        load_menu();
        //To manually assign values
//        InitializeManual();


    }

    private void load_menu() {
        read_more_adapter= new FirebaseRecyclerAdapter<ReadMoreItem,ReadMoreItemViewHolder>(options) {

            @NonNull
            @Override
            public ReadMoreItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.read_more_item, parent, false);

                return new ReadMoreItemViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull ReadMoreItemViewHolder readMoreItemViewHolder, int i, @NonNull ReadMoreItem readMoreItem) {
                readMoreItemViewHolder.value.setText(readMoreItem.getValueOfItem());
            }
        };


        read_more_adapter.startListening();
        readMoreItemRecyclerView.setAdapter(read_more_adapter);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_recycler_view_car_and_location_fall_down);

//        swipeRefreshLayout.setRefreshing(false);
        readMoreItemRecyclerView.setLayoutAnimation(controller);
        Objects.requireNonNull(readMoreItemRecyclerView.getAdapter()).notifyDataSetChanged();
        readMoreItemRecyclerView.scheduleLayoutAnimation();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ReadMoreActivityFromSelectYourPackage.this,SelectYourPackage.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.activity_down_new_activity,R.anim.activity_down_leaving_activty);
    }

    public void InitializeManual(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Services").child("Description nodes").child("Interior");

        String key = reference.push().getKey();

        ReadMoreItem readMoreItem1 = new ReadMoreItem("Vacuum");

        reference.child(key).setValue(readMoreItem1);

        key = reference.push().getKey();

        ReadMoreItem readMoreItem2 = new ReadMoreItem("Carpet Dressing");

        reference.child(key).setValue(readMoreItem2);

        key = reference.push().getKey();

        ReadMoreItem readMoreItem3 = new ReadMoreItem("Fiber/Dashboard Polishing");

        reference.child(key).setValue(readMoreItem3);

        key = reference.push().getKey();

        ReadMoreItem readMoreItem4 = new ReadMoreItem("Mate Polishing");

        reference.child(key).setValue(readMoreItem4);

        key = reference.push().getKey();

        ReadMoreItem readMoreItem5 = new ReadMoreItem("Glass Cleaning");

        reference.child(key).setValue(readMoreItem5);

        key = reference.push().getKey();

        ReadMoreItem readMoreItem6 = new ReadMoreItem("Mate Polishing");

        reference.child(key).setValue(readMoreItem6);

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Services").child("Description nodes").child("Exterior");

        String key2 = reference2.push().getKey();

        ReadMoreItem readMoreItem7 = new ReadMoreItem("Liquid Waxing");

        reference2.child(key2).setValue(readMoreItem7);

        key2 = reference2.push().getKey();

        ReadMoreItem readMoreItem8 = new ReadMoreItem("Tyre Dressing");

        reference2.child(key2).setValue(readMoreItem8);

        key2 = reference2.push().getKey();

        ReadMoreItem readMoreItem9= new ReadMoreItem("Glass Cleaning");

        reference2.child(key2).setValue(readMoreItem9);

        key2 = reference2.push().getKey();

        ReadMoreItem readMoreItem10 = new ReadMoreItem("Fiber Polishing");

        reference2.child(key2).setValue(readMoreItem10);

        key2 = reference2.push().getKey();

        ReadMoreItem readMoreItem11 = new ReadMoreItem("Grill Cleaning");

        reference2.child(key2).setValue(readMoreItem11);

        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference().child("Services").child("Description nodes").child("Interior and Exterior");

        String key3 = reference3.push().getKey();

        ReadMoreItem readMoreItem12 = new ReadMoreItem("Vacuum");

        reference3.child(key3).setValue(readMoreItem12);

        key3 = reference3.push().getKey();

        ReadMoreItem readMoreItem13 = new ReadMoreItem("Carpet Dressing");

        reference3.child(key3).setValue(readMoreItem13);

        key3 = reference3.push().getKey();

        ReadMoreItem readMoreItem14 = new ReadMoreItem("Fiber/Dashboard Polishing");

        reference3.child(key3).setValue(readMoreItem14);

        key3 = reference3.push().getKey();

        ReadMoreItem readMoreItem15 = new ReadMoreItem("Mate Polishing");

        reference3.child(key3).setValue(readMoreItem15);

        key3 = reference3.push().getKey();

        ReadMoreItem readMoreItem16 = new ReadMoreItem("Glass Cleaning");

        reference3.child(key3).setValue(readMoreItem16);

        key3 = reference3.push().getKey();

        ReadMoreItem readMoreItem17 = new ReadMoreItem("Mate Polishing");

        reference3.child(key3).setValue(readMoreItem17);

        key3 = reference3.push().getKey();

        ReadMoreItem readMoreItem18 = new ReadMoreItem("Liquid Waxing");

        reference3.child(key3).setValue(readMoreItem18);

        key3 = reference3.push().getKey();

        ReadMoreItem readMoreItem19 = new ReadMoreItem("Tyre Dressing");

        reference3.child(key3).setValue(readMoreItem19);

        key3 = reference3.push().getKey();

        ReadMoreItem readMoreItem20= new ReadMoreItem("Glass Cleaning");

        reference3.child(key3).setValue(readMoreItem20);

        key3 = reference3.push().getKey();

        ReadMoreItem readMoreItem21 = new ReadMoreItem("Fiber Polishing");

        reference3.child(key3).setValue(readMoreItem21);

        key3 = reference3.push().getKey();

        ReadMoreItem readMoreItem22 = new ReadMoreItem("Grill Cleaning");

        reference3.child(key3).setValue(readMoreItem22);


    }
}
