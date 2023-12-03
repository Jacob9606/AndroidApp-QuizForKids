package com.jacobsbmi.quizforkids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class logoff extends AppCompatActivity {

    TextView text;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logoff);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String User_Name = sharedPreferences.getString("Name", "");
        SharedPreferences spse = getSharedPreferences("History", Context.MODE_PRIVATE);
        int overallpoints = spse.getInt("overallpoints",0);

        text =findViewById(R.id.overallPointsTxt);
        btn = findViewById(R.id.button8);
        text.setText(User_Name + " you have overall "+ overallpoints +" points");

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(logoff.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
