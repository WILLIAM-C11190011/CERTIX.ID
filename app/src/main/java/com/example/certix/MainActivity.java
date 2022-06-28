package com.example.certix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.VoiceInteractor;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;

public class MainActivity extends AppCompatActivity {



    EditText etuser, etpw;
    public static String ip = "http://172.22.0.233:8000";
    //public static String ip = "http://192.168.1.20:8000";

    String url;
    Button b1, b2;
    
    static String x, y;
    public String username, pass;

    private View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.btnsu:
                    goToSignUpPage();
                    break;

                case R.id.button:
                    username = etuser.getText().toString();
                    pass = etpw.getText().toString();

                    if(username.equals("") | pass.equals("")){
                        //ip = "http://192.168.1.18:8000";
                        url = ip;
                        Log.d("URL 1", url);
                    }

                    else{
                        url = ip+"/api/users/"+username;
                        //ip = "http://192.168.1.18:8000/api/users/"+username;
                        Log.d("URL 2", url);
                    }

                    new ReqTask().execute(url, "GET");
                    break;


            }


        }
    };

    private void goToSignUpPage() {
        Intent i = new Intent(this, SignUpPage.class);
        startActivity(i);

    }

    private void checkpass() {

        if(username.equals(x) && pass.equals(y)){
            Toast.makeText(getApplicationContext(), "Log In Berhasil, Selamat Datang, "+x, Toast.LENGTH_LONG).show();
            goToHomePage();

        }
        else if(username.equals("") && pass.equals("")){
            Toast.makeText(getApplicationContext(), "Silahkan Input User Dan Password Anda", Toast.LENGTH_SHORT).show();

        }
        else{

            Toast.makeText(getApplicationContext(), "USER ATAU PASSWORD ANDA SALAH!!!!", Toast.LENGTH_SHORT).show();
            etuser.setText("");
            etpw.setText("");

        }
    }

    private void goToHomePage() {
        Intent i = new Intent(this, HomePage.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

           b1 = findViewById(R.id.button);
           b1.setOnClickListener(clicker);

           b2 = findViewById(R.id.btnsu);
           b2.setOnClickListener(clicker);

           etuser = findViewById(R.id.etusername);
           etpw = findViewById(R.id.etpass);
    }

    class ReqTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

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
                JSONObject DataObj = DataArray.getJSONObject(0);
                x = DataObj.getString("username");
                y = DataObj.getString("pass");
                checkpass();
            }catch (JSONException e){
                checkpass();
                e.printStackTrace();
            }
        }
    }
}

