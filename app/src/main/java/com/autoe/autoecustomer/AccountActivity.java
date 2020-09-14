package com.autoe.autoecustomer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.autoe.autoecustomer.CommonData.Common;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class AccountActivity extends AppCompatActivity {

    CircleImageView accountProfileImage;

    ImageView editProfileImage;

    TextView userName, userEmail;

    private final static int PICK_IMAGE_REQUEST=21;
    FirebaseStorage storage;
    StorageReference storageReference;

    LottieAnimationView loadingImage;

    LinearLayout myLocations, myCars;



    Uri saveUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initialiseVariables();
        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        if(Common.currentUser.getProfilePhoto().equals(""))
        {
            String first_letter_username = Common.currentUser.getName().substring(0,1);
            TextDrawable drawable2 = TextDrawable.builder()
                    .beginConfig().width(100)
                    .height(100)
                    .endConfig()
                    .buildRect(first_letter_username, (R.color.colorPrimary));
            accountProfileImage.setImageDrawable(drawable2);
        }

        else
        {
            Glide.with(AccountActivity.this).load(Common.currentUser.getProfilePhoto())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Toast.makeText(AccountActivity.this, "There was some problem, please try again later", Toast.LENGTH_SHORT).show();
                            loadingImage.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            loadingImage.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(accountProfileImage);
        }

        userName.setText(Common.currentUser.getName());
        userEmail.setText(Common.currentUser.getEmail());

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });



        myLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, MakeABooking.class);

                intent.putExtra("isFromAccount","yes");

                startActivity(intent);
            }
        });

        myCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SelectACar.class);

                intent.putExtra("isFromAccount","yes");

                startActivity(intent);
            }
        });

    }

    private void chooseImage() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&& resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            saveUri= data.getData();

            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setMessage("Update Profile Picture?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            uploadImage();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            //alert.setTitle("AlertDialogExample");
            alert.show();
        }
    }

    private void initialiseVariables()
    {

        accountProfileImage = findViewById(R.id.account_image);

        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);

        editProfileImage = findViewById(R.id.account_image_edit);

        loadingImage = findViewById(R.id.loading_image_animation);

        myCars = findViewById(R.id.ll_cars);
        myLocations = findViewById(R.id.ll_locations);
    }


    private void uploadImage() {
        if(saveUri!=null)
        {
            loadingImage.setVisibility(View.VISIBLE);
           /* final ProgressDialog progressDialog= new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();*/
            String imageName= UUID.randomUUID().toString();
            final StorageReference imageFolder= storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //progressDialog.dismiss();
                    Toast.makeText(AccountActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                        @Override
                        public void onSuccess(Uri uri) {
                            //newCategory= new Category(edtName.getText().toString(),uri.toString());

                            Common.currentUser.setProfilePhoto(uri.toString());

                            Paper.book("user").delete("username");
                            Paper.book("user").write("username",Common.currentUser);

                            DatabaseReference database = FirebaseDatabase.getInstance().getReference("User").child(Common.currentUser.getId());
                            database.setValue(Common.currentUser);
                            Glide.with(AccountActivity.this).load(uri)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            Toast.makeText(AccountActivity.this, "There was some problem, please try again later", Toast.LENGTH_SHORT).show();
                                            loadingImage.setVisibility(View.GONE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            loadingImage.setVisibility(View.GONE);
                                            return false;
                                        }
                                    }).into(accountProfileImage);

                            //loadingImage.setVisibility(View.GONE);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
                            loadingImage.setVisibility(View.GONE);

                            Toast.makeText(AccountActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                }
            });
        }
    }
}
