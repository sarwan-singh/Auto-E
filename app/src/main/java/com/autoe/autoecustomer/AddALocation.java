package com.autoe.autoecustomer;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.autoe.autoecustomer.CommonData.Common;
import com.autoe.autoecustomer.Model.Location;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;

public class AddALocation extends AppCompatActivity{

    private Button Save_button;
    private static final String TAG = "";

    int AUTOCOMPLETE_REQUEST_CODE = 1;

    MaterialEditText placeEditText, placeDescEditText;

    String addressType = "",addressDesc="";

    MaterialSpinner spLablelForPlace;

    int whichLabel;

    LatLng addressLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alocation);

        ImageView backButton = findViewById(R.id.back_button_add_a_location);

        placeEditText = findViewById(R.id.Enter_a_location_edit_text);
        placeDescEditText = findViewById(R.id.label_for_your_car_desc_edit_text);
        spLablelForPlace = findViewById(R.id.label_for_your_car_label_edit_text);

        spLablelForPlace.setItems("Home","Work","Other");

        spLablelForPlace.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                //Toast.makeText(AddALocation.this, item.toString(), Toast.LENGTH_SHORT).show();
                addressType = item.toString();
                whichLabel = position;
            }
        });

        //labelEditText = findViewById(R.id.label_for_your_car_label_edit_text);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });


        Places.initialize(getApplicationContext(), "AIzaSyCQbVdepVtGA01KACwE_YbmdsSfiLDSe_4");

        // Create a new Places client instance.
        Save_button = findViewById(R.id.save_button_add_a_location);

        placeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                    Intent intent = new Autocomplete.IntentBuilder(
                            AutocompleteActivityMode.FULLSCREEN, fields).setCountry("in")
                            .setHint("Start Typing")
                            .build(AddALocation.this);
                    startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

                }
            }
        });

        Save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addressDesc = placeDescEditText.getText().toString();
                if(Objects.requireNonNull(placeEditText.getText()).toString().equals("")) {

                    Toast.makeText(AddALocation.this, "Please enter address", Toast.LENGTH_SHORT).show();
                }else if(addressDesc.equals("")) {

                    Toast.makeText(AddALocation.this, "Please enter Description", Toast.LENGTH_SHORT).show();
                }else{

                    if(addressType.equals(""))
                    {
                        addressType= spLablelForPlace.getText().toString();
                    }
                    final Location location = new Location(String.valueOf(addressLatLng.latitude),
                            String.valueOf(addressLatLng.longitude), addressType,addressDesc);

                    switch (whichLabel)
                    {
                        case 0:
                            Common.currentUser.setHomeAddress(location);
                            break;
                        case 1:
                            Common.currentUser.setOfficeAddress(location);
                            break;
                        case 2:
                            Common.currentUser.setOtherAddress(location);
                            break;
                            default:
                                Toast.makeText(AddALocation.this, "Please select an address", Toast.LENGTH_SHORT).show();
                    }

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");

                    ref.child(Common.currentUser.getId()).setValue(Common.currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(AddALocation.this, "Location Added Successfully", Toast.LENGTH_SHORT).show();
                                if(whichLabel==0)
                                    Common.currentUser.setHomeAddress(location);
                                else if(whichLabel == 1)
                                    Common.currentUser.setOfficeAddress(location);
                                else if(whichLabel==2)
                                    Common.currentUser.setOtherAddress(location);
                                Paper.book("user").delete("username");
                                Paper.book("user").write("username",Common.currentUser);
                                MakeABooking.locationAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                    Intent intent = new Intent(AddALocation.this,MakeABooking.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_back_new_activity,R.anim.activity_back_leaving_activity);
                }
            }
        });
    }

    public void back(){
        Intent intent = new Intent(AddALocation.this,MakeABooking.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddALocation.this,Save_button, ViewCompat.getTransitionName(Save_button));
        startActivity(intent,options.toBundle());
     }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId()+","+(Objects.requireNonNull(place.getLatLng()).latitude));
                placeEditText.setText(place.getName());
                addressLatLng = place.getLatLng();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void showLabelOptions()
    {
    }
}
