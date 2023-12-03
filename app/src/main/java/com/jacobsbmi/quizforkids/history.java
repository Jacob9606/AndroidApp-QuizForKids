package com.jacobsbmi.quizforkids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class history extends AppCompatActivity {

    TextView textView6;
    TextView textView10;
    public Button homeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        SharedPreferences sp = getSharedPreferences("History", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String uname = sp.getString("User_Name", "");
        String game=sp.getString("Game","");
        int overallpoints = sp.getInt("overallpoints",0);
        String storedDateTime = sp.getString("time", "");
        int points = sp.getInt("points",0);

        // Generate the result message
        String head="Hi "+uname+  " you have earned "+ overallpoints+ " points in the following attempts";
        String resultMessage = game+"  area - attempt started on "+ storedDateTime+" – points earned " +points +"\n";

        SharedPreferences sharedPreferences = getSharedPreferences("your_shared_preferences_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        Set<String> savedStrings = sharedPreferences.getStringSet("saved_strings", new HashSet<>());
        savedStrings.add(resultMessage);
        edit.putStringSet("saved_strings", savedStrings);
        edit.apply();

        textView6 = findViewById(R.id.textView6);
        textView10 =findViewById(R.id.textView10);

        SharedPreferences sharedPref = getSharedPreferences("your_shared_preferences_name", Context.MODE_PRIVATE);

        // Retrieve the set of saved strings
        Set<String> res = sharedPref.getStringSet("saved_strings", new HashSet<>());

        // Iterate over the set and process each saved string
        for (String savedString : res) {
            textView10.append(savedString + "\n");
        }

        textView6.setText(head);

        homeButton = findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(history.this, main_page.class);
                startActivity(intent);
                finish();
            }
        });
    }
}