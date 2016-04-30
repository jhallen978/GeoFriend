package com.firebaseio.geofriend.geofriend;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class LobbyActivity extends AppCompatActivity {

    public String fname;
    public String id1;
    public String lname;
    public String pic1;

    TextView fullname;
    TextView fullid;
    ImageView fullpic;
    Button button;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        setTitle("GeoFriend - Lobby");

        fullname = (TextView)findViewById(R.id.textWelcome);
        fullid = (TextView)findViewById(R.id.textId);
        fullpic = (ImageView)findViewById(R.id.imagePic);
        button = (Button) findViewById(R.id.button);
        logoutButton = (Button) findViewById(R.id.buttonLogout);

        Log.d("onCreate: ", "before bundle");
        Bundle inBundle = getIntent().getExtras();
        fname = inBundle.get("fname").toString();
        lname = inBundle.get("lname").toString();
        id1 = inBundle.get("id").toString();
        Log.d("onCreate: ", "before pic1");
        pic1 = inBundle.get("pic").toString();
        Log.d("onCreate: ", "after pic1");
        fullname.setText("Welcome: " + fname + " " + lname);
        fullid.setText("ID: " + id1);

        //execute bitmap task
        new LongOperation().execute("test");

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent map = new Intent(LobbyActivity.this, MapsActivity.class);

                map.putExtra("fname", fname);
                map.putExtra("lname", lname);
                map.putExtra("id", id1);
                map.putExtra("pic", pic1);

                startActivity(map);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent login = new Intent(LobbyActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        protected Bitmap newPic;

        @Override
        protected String doInBackground(String... params) {
            try{
                InputStream is = new URL(pic1).openStream();
                newPic = BitmapFactory.decodeStream( is );

                Log.d("onCreate: ", "set bitmap ");

            }catch(Exception e){
                Log.d("Bitmap/IS exception", e.toString());
            }
            return "OK";
        }

        @Override
        protected void onPostExecute(String result) {
            fullpic.setImageBitmap(newPic);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
