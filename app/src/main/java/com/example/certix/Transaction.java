package com.example.certix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Transaction extends AppCompatActivity {

    ImageButton b1, b2, b3;
    ListView mylv2;
    String url;
    String profile;
    ArrayList<String> event, tiket, price, evenstatus, id, user;

    private View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imageButton:
                    goToHomePage();
                    break;

                case R.id.imageButton2:
                    goToTransaction();
                    break;

                case R.id.imageButton3:
                    goToUser();
                    break;
            }
        }
    };

    private void goToUser() {
        Intent i = new Intent(this, Profile.class);
        startActivity(i);
    }

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
        setContentView(R.layout.activity_transaction);

        profile = MainActivity.x;

        url = MainActivity.ip+"/api/transaksi/";

        event = new ArrayList<>();
        tiket = new ArrayList<>();
        price = new ArrayList<>();
        evenstatus = new ArrayList<>();
        id = new ArrayList<>();
        user = new ArrayList<>();

        mylv2 = (ListView) findViewById(R.id.lv2);

        b1 = findViewById(R.id.imageButton);
        b1.setOnClickListener(clicker);

        b2= findViewById(R.id.imageButton2);
        b2.setOnClickListener(clicker);

        b3 = findViewById(R.id.imageButton3);
        b3.setOnClickListener(clicker);

        Log.d("URL", "TEST");

        new ReqTask().execute(url+profile, "GET");
    }

    class ReqTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.d("URL", strings[0]);

            try{
                URL url = new URL((strings[0]));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod(strings[1]);
                con.setRequestProperty("User-Agent", "Mozilla/5.0");

                if (strings[1] == "GET"){
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String input;
                    StringBuffer response = new StringBuffer();
                    while((input=in.readLine())!=null){
                        response.append(input);
                    }
                    in.close();
                    return String.valueOf(response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String a){
            JSONArray DataArray = null;
            try{
                DataArray = new JSONArray(a);
                Log.d("URL","sini");

                for (int l = 0;l<DataArray.length();l++) {

                    JSONObject DataObj = DataArray.getJSONObject(l);
                    id.add(DataObj.getString("id"));
                    user.add(DataObj.getString("user"));
                    event.add(DataObj.getString("acara"));
                    tiket.add(String.valueOf(DataObj.getInt("jumlahtiket")));
                    price.add(String.valueOf(DataObj.getInt("harga")));
                    evenstatus.add(DataObj.getString("status"));
                    Log.d("URL", String.valueOf(event.get(l)));

                }



                CustomAdapter2 customAdapter = new CustomAdapter2(getBaseContext(), event, tiket, price, evenstatus);
                mylv2.setAdapter(customAdapter);

            }catch (JSONException e){

                e.printStackTrace();
            }
        }
    }
}