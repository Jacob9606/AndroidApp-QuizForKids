package com.jacobsbmi.quizforkids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class animal_result extends AppCompatActivity {
    public Button homeButton;
    public Button playAgainButton;
    public Button cartoonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_result);

        // Get the TextView from the layout
        TextView resultTextView = findViewById(R.id.resultTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        cartoonButton = findViewById(R.id.cartoonButton);
        homeButton = findViewById(R.id.homeButton);

        Intent intent = getIntent();
        int CorrectAnswer = intent.getIntExtra("CorrectAnswer",0);
        int WrongAnswer = intent.getIntExtra("WrongAnswer", 0);

        // Retrieve the data from the intent
        SharedPreferences spse = getSharedPreferences("History", Context.MODE_PRIVATE);
        int overallpoints = spse.getInt("overallpoints",0);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String User_Name = sharedPreferences.getString("Name", "");

            int currentAttemptPoints = CorrectAnswer * 3 - WrongAnswer * 1;
            overallpoints += currentAttemptPoints; // Add current attempt points to overall score

            // Generate the result message
            String resultMessage = "Well done\n " + User_Name + "!\nYou have finished the “Animals” quiz\n"
                    + "with " + CorrectAnswer + " \nand " + WrongAnswer + " \nincorrect answers\n"
                    + "or\n " + currentAttemptPoints + " points for this attempt.\n\n"
                    + "Overall you have " + overallpoints + " points."; // Display overall score
            // Set the result message in the TextView

        SharedPreferences sps = getSharedPreferences("History", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sps.edit();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String currentDateTime = dateFormat.format(new Date());
        editor.putString("time", currentDateTime);
        editor.putString("User_Name", User_Name);
        editor.putString("Game", "Animal");
        editor.putInt("points", currentAttemptPoints);
        editor.putInt("overallpoints", overallpoints);

        editor.apply();

        resultTextView.setText(resultMessage);

        // Set up playAgainButton
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Move to animal_quiz1 activity
                Intent intent = new Intent(animal_result.this, animal_quiz1.class);
                intent.putExtra("CorrectAnswer", 0);
                intent.putExtra("WrongAnswer", 0);
                startActivity(intent);
                finish(); // Finish the current activity
            }
        });

        cartoonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to cartoon_quiz1 activity
                Intent intent = new Intent(animal_result.this, cartoon_quiz1.class);
                startActivity(intent);
                finish(); // Finish the current activity
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to main_page activity
                Intent intent = new Intent(animal_result.this, main_page.class);
                startActivity(intent);

                finish(); // Finish the current activity
            }
        });
        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Expand the menu and add items to the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true; // Return true to display the menu in the action bar
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.playCartoon:
                // cartoon_quiz1 액티비티로 이동하는 코드 작성
                Intent cartoonIntent = new Intent(animal_result.this, cartoon_quiz1.class);
                startActivity(cartoonIntent);
                return true;

            case R.id.playAnimal:
                // animal_quiz1 액티비티로 이동하는 코드 작성
                Intent animalIntent = new Intent(animal_result.this, animal_quiz1.class);
                startActivity(animalIntent);
                return true;

            case R.id.history:
                // history 액티비티로 이동하는 코드 작성
                Intent historyIntent = new Intent(animal_result.this, history.class);
                startActivity(historyIntent);
                return true;

            case R.id.changePasswordMenu:
                // change_password 액티비티로 이동하는 코드 작성
                Intent changePasswordIntent = new Intent(animal_result.this, change_password.class);
                startActivity(changePasswordIntent);
                return true;

            case R.id.instruction:
                // instruction 액티비티로 이동하는 코드 작성
                Intent instructionIntent = new Intent(animal_result.this, instruction.class);
                startActivity(instructionIntent);
                return true;

            default:
                return false;
        }
    }
}
