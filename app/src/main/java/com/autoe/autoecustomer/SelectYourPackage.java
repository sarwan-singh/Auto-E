package com.autoe.autoecustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.autoe.autoecustomer.CommonData.Common;
import com.autoe.autoecustomer.Model.CarOfUser;
import com.autoe.autoecustomer.Model.MainService;
import com.autoe.autoecustomer.Model.OptionalService;
import com.autoe.autoecustomer.ViewHolder.CarViewHolder;
import com.autoe.autoecustomer.ViewHolder.MainServiceViewHolder;
import com.autoe.autoecustomer.ViewHolder.OptionalServiceViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static android.util.Pair.create;

public class SelectYourPackage extends AppCompatActivity implements  com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private ImageView back_button_select_your_package;
    private TextView subtotal_price_textView,next_textView_Select_your_package;
    private int selectedPackagePosition, Year, Month, Day, Hour, Minute, Second;

    private CardView next_button_select_a_package;

    private DatabaseReference table_main_services, table_optional_services;

    private FirebaseRecyclerOptions<MainService> options;
    private FirebaseRecyclerOptions<OptionalService> optionalOptions;
    private RecyclerView main_recycler, optional_recycler;

    private int subtotal_price_main, subtotal_price_optional;

    private FirebaseRecyclerAdapter<MainService, MainServiceViewHolder> main_adapter;
    private FirebaseRecyclerAdapter<OptionalService, OptionalServiceViewHolder> optional_adapter;

    List<String> optionalServicesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_your_package);

        initializeVariables();

        subtotal_price_optional = 0;
        subtotal_price_main = 0;

        selectedPackagePosition = -1;

        optionalServicesList = new ArrayList<>();



        table_main_services = FirebaseDatabase.getInstance().getReference().child("Services").child("Main");
        table_optional_services = FirebaseDatabase.getInstance().getReference().child("Services").child("Optional");
        options= new FirebaseRecyclerOptions.Builder<MainService>()
                .setQuery(table_main_services, MainService.class).build();

        optionalOptions = new FirebaseRecyclerOptions.Builder<OptionalService>()
                .setQuery(table_optional_services, OptionalService.class).build();

        next_button_select_a_package.setVisibility(View.GONE);



        next_button_select_a_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPackagePosition==-1){
                    Toast.makeText(SelectYourPackage.this,"Please select a package to continue",Toast.LENGTH_LONG).show();
                }else {

                    Calendar calendar = Calendar.getInstance();
                    com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(SelectYourPackage.this
                            ,calendar.get(Calendar.YEAR)
                            ,calendar.get(Calendar.MONTH)
                            ,calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.setTitle("Select a date");
                    datePickerDialog.setAccentColor(getResources().getColor(R.color.colorSecondary));
                    datePickerDialog.show(getSupportFragmentManager(),"DatePicker");
                }
/*                DatabaseReference data = FirebaseDatabase.getInstance().getReference("Bookings");

                Common.currentBooking.setDateAdded(String.valueOf(System.currentTimeMillis()));
                Common.currentBooking.setDateOfService(String.valueOf(System.currentTimeMillis()));
                Common.currentBooking.setMainTotal(String.valueOf(subtotal_price_main));
                Common.currentBooking.setOptionalTotal(String.valueOf(subtotal_price_optional));
                Common.currentBooking.setTotalPrice((subtotal_price_textView.getText().toString()));
                data.child(String.valueOf(System.currentTimeMillis())).setValue(Common.currentBooking);
*/
            }
        });


        optional_recycler.setLayoutManager(new LinearLayoutManager(this));
        optional_adapter = new FirebaseRecyclerAdapter<OptionalService, OptionalServiceViewHolder>(optionalOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final OptionalServiceViewHolder optionalServiceViewHolder, int i, @NonNull OptionalService optionalService) {

                optionalServiceViewHolder.txtOptionalServiceLabel.setText(optionalService.getServiceName());
                optionalServiceViewHolder.txtOptionalServiceDescription.setText(optionalService.getServiceDescription());
                optionalServiceViewHolder.txtOptionalServicePrice.setText(optionalService.getServicePrice());

            }

            @NonNull
            @Override
            public OptionalServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.optional_service_item, parent, false);

                return new OptionalServiceViewHolder(itemView);
            }
        };
        main_recycler.setLayoutManager(new LinearLayoutManager(this));
        main_adapter = new FirebaseRecyclerAdapter<MainService,MainServiceViewHolder>(options) {

            @NonNull
            @Override
            public MainServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_service_item, parent, false);

                return new MainServiceViewHolder(itemView);

            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                //checkStatus();
            }

            @Override
            protected void onBindViewHolder(@NonNull final MainServiceViewHolder mainServiceViewHolder, int i, @NonNull MainService mainService) {

                mainServiceViewHolder.txtMainServiceLabel.setText(mainService.getServiceName());
                mainServiceViewHolder.txtMainServiceDescription.setText(mainService.getServiceDescription());

                switch (Common.selectedCarType) {
                    case Common.HATCH_BACK:
                        mainServiceViewHolder.txtMainServicePrice.setText(mainService.getServicePriceCarHatchBack());
                        break;
                    case Common.SEDAN:
                        mainServiceViewHolder.txtMainServicePrice.setText(mainService.getServicePriceCarSedan());
                        break;
                    case Common.XUV:
                        mainServiceViewHolder.txtMainServicePrice.setText(mainService.getServicePriceCarXUV());
                        break;
                }

                if (selectedPackagePosition==i){
                    mainServiceViewHolder.txtMainServiceLabel.setTextColor(getResources().getColor(R.color.color_selected_car));
                    mainServiceViewHolder.txtMainServicePrice.setTextColor(getResources().getColor(R.color.colorSecondary));
                    mainServiceViewHolder.txtMainServiceReadMore.setTextColor(getResources().getColor(R.color.colorSecondary));
                    mainServiceViewHolder.rupeeSymbolImageViewMainService.setImageResource(R.drawable.rupee_icon);
                    mainServiceViewHolder.radioBoxImageViewMainService.setImageResource(R.drawable.selected_image_view_checked);

                    Common.currentBooking.setMainPlan(mainServiceViewHolder.txtMainServiceLabel.getText().toString());

                }else{
                    mainServiceViewHolder.txtMainServiceLabel.setTextColor(getResources().getColor(R.color.color_not_selected_car));
                    mainServiceViewHolder.txtMainServicePrice.setTextColor(getResources().getColor(R.color.color_not_selected_car));
                    mainServiceViewHolder.txtMainServiceReadMore.setTextColor(getResources().getColor(R.color.color_not_selected_car));
                    mainServiceViewHolder.rupeeSymbolImageViewMainService.setImageResource(R.drawable.rupee_icon_not_selected);
                    mainServiceViewHolder.radioBoxImageViewMainService.setImageResource(R.drawable.unselected_image_view_unchecked);


                    Common.currentBooking.setMainPlan("");
                }

                mainServiceViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedPackagePosition>= 0)
                            notifyItemChanged(selectedPackagePosition);
                        if (selectedPackagePosition == mainServiceViewHolder.getAdapterPosition()){
                            selectedPackagePosition = -1;
                            subtotal_price_main = subtotal_price_main - Integer.parseInt(mainServiceViewHolder.txtMainServicePrice.getText().toString());

                            Log.v("subtotal","sub" + subtotal_price_main);

                            subtotal_price_textView.setText(String.valueOf(subtotal_price_main+subtotal_price_optional));
                            notifyItemChanged(selectedPackagePosition);
                        }else {
                            subtotal_price_main = subtotal_price_main + Integer.parseInt(mainServiceViewHolder.txtMainServicePrice.getText().toString());

                            Log.v("subtotal","sub" + subtotal_price_main);

                            subtotal_price_textView.setText(String.valueOf(subtotal_price_main+subtotal_price_optional));
                            selectedPackagePosition= mainServiceViewHolder.getAdapterPosition();
                            notifyItemChanged(selectedPackagePosition);
                        }
                    }
                });

                if (selectedPackagePosition !=-1){
                    next_textView_Select_your_package.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                }else {
                    next_textView_Select_your_package.setBackgroundColor(getResources().getColor(R.color.color_not_selected_car));
                }
            }

        };



        load_main_services();

        load_optional_services();

        back_button_select_your_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectYourPackage.this,SelectACar.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_back_new_activity,R.anim.activity_back_leaving_activity);
            }
        });

//        To add services manually to database

//        MainServicesInitializeManual();

//        OptionalServicesInitializeManual();

    }


    private void load_main_services() {

        options= new FirebaseRecyclerOptions.Builder<MainService>()
                .setQuery(table_main_services,MainService.class).build();
        main_recycler.setLayoutManager(new LinearLayoutManager(this));
        main_adapter= new FirebaseRecyclerAdapter<MainService, MainServiceViewHolder>(options) {
            @NonNull
            @Override
            public MainServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_service_item, parent,false);

                return new MainServiceViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull final MainServiceViewHolder mainServiceViewHolder, int i, @NonNull MainService mainService) {

                mainServiceViewHolder.txtMainServiceLabel.setText(mainService.getServiceName());
                mainServiceViewHolder.txtMainServiceDescription.setText(mainService.getServiceDescription());switch (Common.selectedCarType) {
                    case Common.HATCH_BACK:
                        mainServiceViewHolder.txtMainServicePrice.setText(mainService.getServicePriceCarHatchBack());
                        break;
                    case Common.SEDAN:
                        mainServiceViewHolder.txtMainServicePrice.setText(mainService.getServicePriceCarSedan());
                        break;
                    case Common.XUV:
                        mainServiceViewHolder.txtMainServicePrice.setText(mainService.getServicePriceCarXUV());
                        break;
                }


                if (selectedPackagePosition==i){
                    mainServiceViewHolder.txtMainServiceLabel.setTextColor(getResources().getColor(R.color.color_selected_car));
                    mainServiceViewHolder.txtMainServicePrice.setTextColor(getResources().getColor(R.color.colorSecondary));
                    mainServiceViewHolder.txtMainServiceReadMore.setTextColor(getResources().getColor(R.color.colorSecondary));
                    mainServiceViewHolder.rupeeSymbolImageViewMainService.setImageResource(R.drawable.rupee_icon);
                    mainServiceViewHolder.radioBoxImageViewMainService.setImageResource(R.drawable.selected_image_view_checked);
                    Common.currentBooking.setMainPlan(mainServiceViewHolder.txtMainServiceLabel.getText().toString());

                }else{
                    mainServiceViewHolder.txtMainServiceLabel.setTextColor(getResources().getColor(R.color.color_not_selected_car));
                    mainServiceViewHolder.txtMainServicePrice.setTextColor(getResources().getColor(R.color.color_not_selected_car));
                    mainServiceViewHolder.txtMainServiceReadMore.setTextColor(getResources().getColor(R.color.color_not_selected_car));
                    mainServiceViewHolder.rupeeSymbolImageViewMainService.setImageResource(R.drawable.rupee_icon_not_selected);
                    mainServiceViewHolder.radioBoxImageViewMainService.setImageResource(R.drawable.unselected_image_view_unchecked);

                    Common.currentBooking.setMainPlan("");
                }

                mainServiceViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedPackagePosition>= 0)
                            notifyItemChanged(selectedPackagePosition);
                        if (selectedPackagePosition == mainServiceViewHolder.getAdapterPosition()){
                            selectedPackagePosition = -1;
                            subtotal_price_main = subtotal_price_main - Integer.parseInt(mainServiceViewHolder.txtMainServicePrice.getText().toString());

                            Log.v("subtotal","sub" + subtotal_price_main);

                            subtotal_price_textView.setText(String.valueOf(subtotal_price_main+subtotal_price_optional));
                            notifyItemChanged(selectedPackagePosition);
                        }else {
                            subtotal_price_main =  Integer.parseInt(mainServiceViewHolder.txtMainServicePrice.getText().toString());

                            Log.v("subtotal","sub" + subtotal_price_main);

                            subtotal_price_textView.setText(String.valueOf(subtotal_price_main+subtotal_price_optional));
                            selectedPackagePosition= mainServiceViewHolder.getAdapterPosition();
                            notifyItemChanged(selectedPackagePosition);
                        }
                    }
                });

                mainServiceViewHolder.txtMainServiceReadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedPackagePosition==mainServiceViewHolder.getAdapterPosition()) {
                            mainServiceViewHolder.txtMainServicePrice.setTransitionName("price select your package to read more");
                            mainServiceViewHolder.txtMainServiceLabel.setTransitionName("label select your package to read more");
                            Pair<View, String> p1 = create((View) mainServiceViewHolder.txtMainServicePrice, "price select your package to read more");
                            Pair<View, String> p2 = create((View) mainServiceViewHolder.txtMainServiceLabel, "label select your package to read more");
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SelectYourPackage.this, p1, p2);
                            Intent intent = new Intent(SelectYourPackage.this, ReadMoreActivityFromSelectYourPackage.class);
                            intent.putExtra("price value select your value read more", mainServiceViewHolder.txtMainServicePrice.getText().toString());
                            intent.putExtra("label value select your value read more", mainServiceViewHolder.txtMainServiceLabel.getText().toString());
                            intent.putExtra("description value select your value read more", mainServiceViewHolder.txtMainServiceDescription.getText().toString());
                            startActivity(intent, options.toBundle());}
                    }
                });

                if (selectedPackagePosition !=-1){
                    next_textView_Select_your_package.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                }else {
                    next_textView_Select_your_package.setBackgroundColor(getResources().getColor(R.color.color_not_selected_car));
                }
                Log.v("atwewqr","234324"+mainService.getServiceName());
                Log.v("data","12354"+mainServiceViewHolder.txtMainServicePrice.getText().toString()+mainServiceViewHolder.txtMainServiceDescription.getText().toString());

            }
        };


        main_adapter.startListening();
        main_recycler.setAdapter(main_adapter);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_recycler_view_car_and_location_fall_down);

//        swipeRefreshLayout.setRefreshing(false);
        main_recycler.setLayoutAnimation(controller);
        Objects.requireNonNull(main_recycler.getAdapter()).notifyDataSetChanged();
        main_recycler.scheduleLayoutAnimation();



        Log.v("number of items: ","abcdabcdabcdbefore"+ main_adapter.getItemCount());


    }

    private void load_optional_services() {

        optionalOptions= new FirebaseRecyclerOptions.Builder<OptionalService>()
                .setQuery(table_optional_services,OptionalService.class).build();
        optional_recycler.setLayoutManager(new LinearLayoutManager(this));
        optional_adapter= new FirebaseRecyclerAdapter<OptionalService, OptionalServiceViewHolder>(optionalOptions) {
            @NonNull
            @Override
            public OptionalServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.optional_service_item,parent,false);

                return new OptionalServiceViewHolder(itemView);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                next_button_select_a_package.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onBindViewHolder(@NonNull final OptionalServiceViewHolder optionalServiceViewHolder, int i, @NonNull final OptionalService optionalService) {

                optionalServiceViewHolder.txtOptionalServiceLabel.setText(optionalService.getServiceName());
                optionalServiceViewHolder.txtOptionalServiceDescription.setText(optionalService.getServiceDescription());
                optionalServiceViewHolder.txtOptionalServicePrice.setText(optionalService.getServicePrice());


               /* optionalServiceViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedPackagePosition>= 0)
                            notifyItemChanged(selectedPackagePosition);
                        if (selectedPackagePosition == optionalServiceViewHolder.getAdapterPosition()){
                            selectedPackagePosition = -1;
                            subtotal_price = subtotal_price - Integer.parseInt(optionalServiceViewHolder.txtOptionalServicePrice.getText().toString());

                            Log.v("subtotal","sub" + subtotal_price);

                            subtotal_price_textView.setText(String.valueOf(subtotal_price));
                            notifyItemChanged(selectedPackagePosition);
                        }else {
                            subtotal_price =  Integer.parseInt(optionalServiceViewHolder.txtOptionalServicePrice.getText().toString());

                            Log.v("subtotal","sub" + subtotal_price);

                            subtotal_price_textView.setText(String.valueOf(subtotal_price));
                            selectedPackagePosition= optionalServiceViewHolder.getAdapterPosition();
                            notifyItemChanged(selectedPackagePosition);
                        }
                    }
                });
*/
                /*if (selectedPackagePosition !=-1){
                    next_textView_Select_your_package.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                }*//*else {
                    next_textView_Select_your_package.setBackgroundColor(getResources().getColor(R.color.color_not_selected_car));
                }
                Log.v("atwewqr","234324"+mainService.getServiceName());
                Log.v("data","12354"+mainServiceViewHolder.txtMainServicePrice.getText().toString()+mainServiceViewHolder.txtMainServiceDescription.getText().toString());


*/

//                optionalServiceViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(optionalServiceViewHolder.checkBoxImageViewOptionalService.isChecked()){
//                            subtotal_price_optional = subtotal_price_optional - Integer.parseInt(optionalServiceViewHolder.txtOptionalServicePrice.getText().toString());
//
//                            subtotal_price_textView.setText(String.valueOf(subtotal_price_optional+subtotal_price_main));
//
//                            optionalServiceViewHolder.checkBoxImageViewOptionalService.setChecked(false);
//                        }else{
//                            Log.d("Optional",optionalServiceViewHolder.txtOptionalServicePrice.getText().toString());
//
//                            subtotal_price_optional = subtotal_price_optional + Integer.parseInt(optionalServiceViewHolder.txtOptionalServicePrice.getText().toString());
//
//                            subtotal_price_textView.setText(String.valueOf(subtotal_price_optional+subtotal_price_main));
//                            optionalServiceViewHolder.checkBoxImageViewOptionalService.setChecked(true);
//
//                        }
//                    }
//                });



                optionalServiceViewHolder.checkBoxImageViewOptionalService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                        {
                            Log.d("Optional",optionalServiceViewHolder.txtOptionalServicePrice.getText().toString());

                            subtotal_price_optional = subtotal_price_optional + Integer.parseInt(optionalServiceViewHolder.txtOptionalServicePrice.getText().toString());

                            subtotal_price_textView.setText(String.valueOf(subtotal_price_optional+subtotal_price_main));

                            optionalServicesList.add(optionalServiceViewHolder.txtOptionalServiceLabel.getText().toString());

                        }

                        else
                        {
                            subtotal_price_optional = subtotal_price_optional - Integer.parseInt(optionalServiceViewHolder.txtOptionalServicePrice.getText().toString());

                            subtotal_price_textView.setText(String.valueOf(subtotal_price_optional+subtotal_price_main));

                            optionalServicesList.remove(optionalServiceViewHolder.txtOptionalServiceLabel.getText().toString());
                        }
                    }
                });
            }
        };


        optional_adapter.startListening();
        optional_recycler.setAdapter(optional_adapter);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_recycler_view_car_and_location_fall_down);

//        swipeRefreshLayout.setRefreshing(false);
        optional_recycler.setLayoutAnimation(controller);
        Objects.requireNonNull(optional_recycler.getAdapter()).notifyDataSetChanged();
        optional_recycler.scheduleLayoutAnimation();


        Log.v("number of items: ","abcdabcdabcdbefore"+ main_adapter.getItemCount());

    }
    @Override
    protected void onStop() {
        super.onStop();
        optional_adapter.stopListening();
        main_adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (main_adapter!=null)
            main_adapter.startListening();
        if (optional_adapter!=null)
            optional_adapter.startListening();
    }


    private void initializeVariables() {
        back_button_select_your_package = findViewById(R.id.back_button_select_your_package);
        optional_recycler = findViewById(R.id.recycler_view_optional_plans);
        main_recycler = findViewById(R.id.recycler_view_main_packages);
        next_button_select_a_package = findViewById(R.id.next_button_select_your_package);
        subtotal_price_textView = findViewById(R.id.subtotal_price_textView_select_your_package);
        next_textView_Select_your_package = findViewById(R.id.next_textView_select_your_package);

    }
    @Override
    public void onTimeSet(com.wdullaer.materialdatetimepicker.time.TimePickerDialog view, int hourOfDay, int minute, int second) {
        Hour = hourOfDay;
        Minute = minute;
        Second = second;

        Intent intent = new Intent(SelectYourPackage.this,BookingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_switch_new_activity,R.anim.activity_switch_leaving_activity);


    }
    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        Year = year;
        Month = monthOfYear;
        Day = dayOfMonth;

        Calendar calendar = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog timePickerDialog = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(SelectYourPackage.this
                ,calendar.get(Calendar.HOUR_OF_DAY)
                ,calendar.get(Calendar.MINUTE)
                ,true);
        timePickerDialog.setTitle("Choose a time");
        timePickerDialog.setAccentColor(getResources().getColor(R.color.colorSecondary));
        timePickerDialog.show(getSupportFragmentManager(),"TimePicker");

    }

        /*private void OptionalServicesInitializeManual() {

        String key = referenceServices.push().getKey();

        OptionalService newOptionalService1 = new OptionalService("Dry cleaning(Interior)",key,"699","Optional services","");

        referenceServices.child("Optional").child(key).setValue(newOptionalService1);

        key = referenceServices.push().getKey();

        OptionalService newOptionalService2 = new OptionalService("Clay bar treatment",key,"899","Optional services","");

        referenceServices.child("Optional").child(key).setValue(newOptionalService2);

        key = referenceServices.push().getKey();

        OptionalService newOptionalService3 = new OptionalService("Scratch removal",key,"150","Optional services","");

        referenceServices.child("Optional").child(key).setValue(newOptionalService3);

        key = referenceServices.push().getKey();

        OptionalService newOptionalService4 = new OptionalService("Water spots removal",key,"100","Optional services","");

        referenceServices.child("Optional").child(key).setValue(newOptionalService4);

        key = referenceServices.push().getKey();

        OptionalService newOptionalService5 = new OptionalService("Dress plastic",key,"100","Optional services","");

        referenceServices.child("Optional").child(key).setValue(newOptionalService5);

        key = referenceServices.push().getKey();

        OptionalService newOptionalService6 = new OptionalService("Headlight Restoration",key,"900","Optional services","");

        referenceServices.child("Optional").child(key).setValue(newOptionalService6);

        key = referenceServices.push().getKey();

        OptionalService newOptionalService7 = new OptionalService("Buffing and Rubbing",key,"999","Optional services","");

        referenceServices.child("Optional").child(key).setValue(newOptionalService7);

        key = referenceServices.push().getKey();

        OptionalService newOptionalService8 = new OptionalService("Dry cleaning(Exterior and Interior)",key,"899","Optional services","");

        referenceServices.child("Optional").child(key).setValue(newOptionalService8);

        key = referenceServices.push().getKey();

        OptionalService newOptionalService9 = new OptionalService("Dry cleaning, Rubbing and Buffing",key,"1499","Optional services","");

        referenceServices.child("Optional").child(key).setValue(newOptionalService9);

    }

    private void MainServicesInitializeManual() {

        String key = referenceServices.push().getKey();

        MainService newMainService1 = new MainService("Interior","MainService Description Interior",key,"250","300","200","Main plans");

        referenceServices.child("Main").child(key).setValue(newMainService1);

        key = referenceServices.push().getKey();

        MainService newMainService2 = new MainService("Exterior","MainService Description Exterior",key,"250","300","200","Main plans");

        referenceServices.child("Main").child(key).setValue(newMainService2);

        key = referenceServices.push().getKey();

        MainService newMainService3 = new MainService("Interior and Exterior","MainService Description Interior and Exterior",key,"400","500","300","Main plans");

        referenceServices.child("Main").child(key).setValue(newMainService3);

    }
*/
}
