package com.example.certix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class concertinfo extends AppCompatActivity {

    Button book, tambah, kurang;
    String x;
    TextView infoacara, infoharga, itemcount, total;
    String url1, url2, id;
    Integer count = 0;
    Integer sum, y, z;
    String profile;
    String paystatus;

    private View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            new ReqTask().execute(url1+id+"/slot", "GET");

            switch (view.getId()){
                case R.id.btntambah:
                    add();
                    break;


                case R.id.btnkurang:
                    substract();
                    break;

                case R.id.btnbook:
                    new ReqTask().execute(url2, "POST");
                    goToHomePage();
                    break;


            }
        }
    };

    private void goToHomePage() {
        Intent i = new Intent(this, HomePage.class);
        startActivity(i);
    }

    private void substract() {
        if(count>0) {
            count = count - 1;
            sum = (count*(y));
            itemcount.setText("" +count);
            total.setText("Total : Rp."+sum);
        }

        else{
            count = 0;
            sum = (count*(y));
            itemcount.setText("" +count);
            total.setText("Total : Rp."+sum);
        }
    }

    private void add() {

        if(count < z){
            count = count+1;
            sum = (count*(y));
            itemcount.setText(""+count);
            total.setText("Total : Rp."+sum);
        }

        else{
            count = z;
            sum = (count*(y));
            total.setText("Total : Rp."+sum);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concertinfo);

        paystatus = "PENDING";

        url1 = MainActivity.ip+"/api/datakonser/";
        url2 = MainActivity.ip+"/api/transaksi";

        profile = MainActivity.x;

        book = findViewById(R.id.btnbook);
        tambah = findViewById(R.id.btntambah);
        kurang = findViewById(R.id.btnkurang);

        total =(TextView) findViewById(R.id.totalharga);

        infoacara = (TextView) findViewById(R.id.acarainfotv);
        infoharga = (TextView) findViewById(R.id.hargainfotv);

        itemcount = (TextView) findViewById(R.id.tvitemcount);

        tambah.setOnClickListener(clicker);
        kurang.setOnClickListener(clicker);
        book.setOnClickListener(clicker);

        id = getIntent().getStringExtra("x");

        new ReqTask().execute(url1+id, "GET");

        //sum =  sum = (count*Integer.parseInt(y));
        itemcount.setText(""+count);
        total.setText("Total:Rp. "+count);



    }

    class ReqTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL((strings[0]));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod(strings[1]);
                con.setRequestProperty("User-Agent", "Mozilla/5.0");

                if (strings[1] == "GET") {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String input;
                    StringBuffer response = new StringBuffer();
                    while ((input = in.readLine()) != null) {
                        response.append(input);
                    }
                    in.close();
                    return String.valueOf(response);
                }

                else if (strings[1] == "POST") {
                    con.setRequestMethod(strings[1]);
                    con.setRequestProperty("Content-type", "application/json");
                    con.setDoOutput(false);
                    con.setDoInput(true);

                    JSONObject DataObj = new JSONObject();
                    DataObj.put("user", profile);
                    DataObj.put("acara", x);
                    DataObj.put("jumlahtiket", count);
                    DataObj.put("harga", sum);
                    DataObj.put("status", paystatus);

                    //Log.d("test", DataObj.toString());

                    OutputStream os = new BufferedOutputStream(con.getOutputStream());
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    out.write(DataObj.toString());
                    out.flush();
                    out.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String input;
                    while ((input = in.readLine()) != null) {
                        Log.d("test", input);
                    }
                    return "ok";
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONArray DataArray = null;
            try {
                DataArray = new JSONArray(s);
                JSONObject DataObj = DataArray.getJSONObject(0);
                x = DataObj.getString("Acara");
                y = DataObj.getInt("Harga");
                z = DataObj.getInt("Slot");
                infoacara.setText((x));
                infoharga.setText(("Harga: Rp."+y));
                //total.setText("Total: Rp. "+sum);

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

    }
}