package com.example.certix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    ImageButton b7, b8, b9;
    Button b10;

    String profile;
    TextView unameview;


    private View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imageButton7:
                    goToHomePage();
                    break;

                case R.id.imageButton8:
                    goToTransaction();
                    break;

//                case R.id.imageButton9:
//                    goToUser();
//                    break;

                case R.id.logoutbtn:
                    goToMainActivity();
                    break;
            }
        }
    };

    private void goToMainActivity() {

        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }




//    private void goToUser() {
//        Intent i = new Intent(this, Profile.class);
//        startActivity(i);
//    }

    private void goToTransaction() {
        Intent i = new Intent(this, Transaction.class);
        startActivity(i);

    }

    private void goToHomePage() {
        Intent i = new Intent(this, HomePage.class);
        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        unameview = (TextView) findViewById(R.id.tvprofile);
        profile = MainActivity.x;
        unameview.setText("Hello "+profile);

        b7 = findViewById(R.id.imageButton7);
        b7.setOnClickListener(clicker);

        b8 = findViewById(R.id.imageButton8);
        b8.setOnClickListener(clicker);

        b9 = findViewById(R.id.imageButton9);
        b9.setOnClickListener(clicker);

        b10 = findViewById(R.id.logoutbtn);
        b10.setOnClickListener(clicker);
    }
}