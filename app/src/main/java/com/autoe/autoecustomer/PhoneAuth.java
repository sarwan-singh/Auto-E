package com.autoe.autoecustomer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class PhoneAuth extends AppCompatActivity {

    private EditText editTextMobile;
    private FirebaseAuth mAuth;
    static String name;
    static String email;
    DatabaseReference referTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        editTextMobile = findViewById(R.id.editTextMobile);
        name= getIntent().getExtras().getString("name");
        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobile = editTextMobile.getText().toString().trim();

                if (mobile.isEmpty() || mobile.length() < 10) {
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }


                    Intent intent = new Intent(PhoneAuth.this, PhoneVerification.class);
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("name", getIntent().getExtras().getString("name"));
                    intent.putExtra("id", getIntent().getExtras().getString("id"));
                    intent.putExtra("profilePhoto",getIntent().getExtras().getString("profilePhoto"));
                    intent.putExtra("email",getIntent().getExtras().getString("email"));
                    startActivity(intent);

                }

        });
    }
/*    public static String random() {
        final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

        String test = name.trim().replace(" ","");

        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(5);
        for(int i=0;i<5;++i) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
            sb.append(test.charAt(i));
        }
        return sb.toString();
    }*/
}
