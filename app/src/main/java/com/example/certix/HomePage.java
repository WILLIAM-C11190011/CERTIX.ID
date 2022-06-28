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

public class HomePage extends AppCompatActivity {

    ImageButton b4, b5, b6;
    ListView mylv;
    String url;

    ArrayList<String> acara, harga, slot,id;


    private View.OnClickListener clicker2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imageButton4:
                    goToHomePage();
                    break;

                case R.id.imageButton5:
                     goToTransaction();
                     break;

                case R.id.imageButton6:
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

    private void goToinfo(){
        Intent i = new Intent(this, concertinfo.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //String ip = "http://192.168.1.18:8000/api/datakonser";
        //String ip = "http://172.22.2.18:8000/api/datakonser";
        http://172.22.2.18:8000/
        //ip "/api/datakonser";
        url = MainActivity.ip+"/api/datakonser";

        acara = new ArrayList<>();
        slot = new ArrayList<>();
        harga = new ArrayList<>();
        id = new ArrayList<>();
        mylv = (ListView) findViewById(R.id.lv1);

        b4 = findViewById(R.id.imageButton4);
        b4.setOnClickListener(clicker2);

        b5 = findViewById(R.id.imageButton5);
        b5.setOnClickListener(clicker2);

        b6 = findViewById(R.id.imageButton6);
        b6.setOnClickListener(clicker2);
        new ReqTask().execute(url, "GET");
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

                for (int l = 0;l<DataArray.length();l++) {

                    JSONObject DataObj = DataArray.getJSONObject(l);
                    id.add(DataObj.getString("ID"));
                    acara.add(DataObj.getString("Acara"));
                    slot.add(String.valueOf(DataObj.getInt("Slot")));
                    harga.add(String.valueOf(DataObj.getInt("Harga")));

                }

                Log.d("URL","mlv");
                CustomAdapter1 customAdapter1 = new CustomAdapter1(getApplicationContext(), acara, slot, harga);
                mylv.setAdapter(customAdapter1);

                mylv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                       Intent v = new Intent(getApplicationContext(), concertinfo.class);
                       v.putExtra("x",id.get(i));
                       startActivity(v);

                               //Toast.makeText(getApplicationContext(), ""+i, Toast.LENGTH_SHORT).show();
                   }
               });

            }catch (JSONException e){

                e.printStackTrace();
            }
        }
    }
}