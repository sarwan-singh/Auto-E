package com.autoe.autoecustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.autoe.autoecustomer.Model.CarOfUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Objects;

public class AddACar extends AppCompatActivity {

    private ImageView back_button_add_a_car;

    private Button save_add_a_car;

    private DatabaseReference table_car;

    private FirebaseUser firebaseUser;

    private String typeOfCar;

    private int positionOfTypeOfCar;

    private ProgressBar progressBar;

    MaterialSpinner spTypeOfCar;
    ArrayList<String> car_of_user = new ArrayList<>();


    MaterialEditText carDesc, label;
    ArrayAdapter<String> dataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acar);



        table_car = FirebaseDatabase.getInstance().getReference("Cars");

        spTypeOfCar = findViewById(R.id.select_your_type_of_car_edit_text);

        spTypeOfCar.setItems("Hatch Back","Sedan","XUV");

        spTypeOfCar.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                typeOfCar = item.toString();
                positionOfTypeOfCar = position;
            }
        });

        initializeVariables();

        table_car.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot carTypeDatabase : dataSnapshot.getChildren()){

                    CarOfUser carOfUser = carTypeDatabase.getValue(CarOfUser.class);
                    Log.d("Cars",carOfUser.getType());

                    //arrayList = new ArrayList<>();

                    // arrayList.add(carType);
                    car_of_user.add(carOfUser.getType());

                    //spTypeOfCar.append(carType.getCarType());
                    //spTypeOfCar;


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, car_of_user);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spTypeOfCar.setAdapter(dataAdapter);
        back_button_add_a_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToSelectACar();
            }
        });


        //TODO
        // Check If Condition

        save_add_a_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_add_a_car.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                String carDescText = Objects.requireNonNull(carDesc.getText()).toString();
                String carLabelText = Objects.requireNonNull(label.getText()).toString();
                if(carDescText.isEmpty()||carLabelText.isEmpty()){
                    Toast.makeText(AddACar.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    save_add_a_car.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                else
                {
                    final CarOfUser car = new CarOfUser(spTypeOfCar.getText().toString(),
                            carDesc.getText().toString(),
                            label.getText().toString());

                    final DatabaseReference ref = FirebaseDatabase.getInstance()
                            .getReference("Cars");

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            ref.child(firebaseUser.getUid()).child(String.valueOf(System.currentTimeMillis())).setValue(car)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AddACar.this,"Sorry, an error occurred, please try again later.",Toast.LENGTH_LONG).show();
                                            save_add_a_car.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AddACar.this,"Your car has been successfully added",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(AddACar.this,SelectACar.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.activity_back_new_activity,R.anim.activity_back_leaving_activity);
                                    save_add_a_car.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    },3000);
                }
            }
        });
    }

    private void initializeVariables() {

        back_button_add_a_car = findViewById(R.id.back_button_add_a_car);

        save_add_a_car = findViewById(R.id.save_button_add_a_car);

        carDesc = findViewById(R.id.describe_your_car_edit_text);

        label = findViewById(R.id.choose_a_label_for_your_car_edit_text);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        progressBar = findViewById(R.id.progress_circular_add_a_car);
    }

    public void backToSelectACar(){
        Intent intent = new Intent(AddACar.this,SelectACar.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_back_new_activity,R.anim.activity_back_leaving_activity);
    }

    @Override
    public void onBackPressed() {
        backToSelectACar();
    }

}
