package com.example.certix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignUpPage extends AppCompatActivity {

    EditText suuser, supw;
    Button b1;
    String url;
    String username, pass;
    String errormsg;
    static String x, y;

    private View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.signupbutton:

                    username = suuser.getText().toString();
                    pass = supw.getText().toString();
                    new  ReqTask().execute(url+"/api/signup/"+username, "POST");
                    Log.d("TEST", url);
                    break;
            }
        }
    };

    private void goToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        url = MainActivity.ip;
        suuser = findViewById(R.id.etusersignup);
        supw = findViewById(R.id.etpwsignup);

        b1 = findViewById(R.id.signupbutton);
        b1.setOnClickListener(clicker);


    }

    class ReqTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try{
                URL url = new URL((strings[0]));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod(strings[1]);
                con.setRequestProperty("User-Agent", "Mozilla/5.0");

                if (strings[1] == "POST"){
                    con.setRequestMethod(strings[1]);
                    con.setRequestProperty("Content-type", "application/json");
                    con.setDoOutput(false);
                    con.setDoInput(true);

                    JSONObject DataObj = new JSONObject();
                    DataObj.put("username", username);
                    DataObj.put("pass", pass);

                    OutputStream os = new BufferedOutputStream(con.getOutputStream());
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    out.write(DataObj.toString());
                    out.flush();
                    out.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String input;
                    StringBuffer response = new StringBuffer();

                    while((input=in.readLine())!=null){
                        response.append(input);
                        errormsg = input;
                        Log.d("error", errormsg);


                    }
                    //in.close();
                    return String.valueOf(response);
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
        protected void onPostExecute(String a){
           Log.d("error", errormsg);
           if(errormsg.equals("error")){

               Toast.makeText(getApplicationContext(), "Username Sudah Terpakai", Toast.LENGTH_LONG).show();

           }
           else{
               Toast.makeText(getApplicationContext(), "Berhasil Sign Up", Toast.LENGTH_LONG).show();
               goToMain();
           }

        }
    }
}