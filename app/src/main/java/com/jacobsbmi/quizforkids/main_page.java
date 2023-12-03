package com.jacobsbmi.quizforkids;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
public class main_page extends AppCompatActivity {
    private ImageView logoImage;
    private Button animalButton;
    private Button cartoonButton;
    public Button logoffButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        // Initialize logoImage
        logoImage = findViewById(R.id.logoImage);

        // Initialize animalButton
        animalButton = findViewById(R.id.animalButton);
        cartoonButton = findViewById(R.id.cartoonButton);
        logoffButton = findViewById(R.id.logoffButton);

        // Set OnClickListener for logoffButton
        logoffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Intent to launch activity_main activity
                Intent intent = new Intent(main_page.this, logoff.class);
                startActivity(intent);
            }
        });
        // Set OnClickListener for animalButton
        animalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Intent to launch animal_quiz1 activity
                Intent intent = new Intent(main_page.this, animal_quiz1.class);
                startActivity(intent);
            }
        });
        // Set OnClickListener for cartoonButton
        cartoonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Intent to launch cartoon_quiz1 activity
                Intent intent = new Intent(main_page.this, cartoon_quiz1.class);
                startActivity(intent);
            }
        });
        // Apply fade-in and fade-out animation to logoImage
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                logoImage.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                logoImage.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        logoImage.startAnimation(fadeIn);

        // 액션바를 설정합니다. 상단에 위치한 보라색 바
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // 메뉴를 확장하여 액션바에 아이템을 추가합니다.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true; //true를 리턴하면 메뉴가 액션바에 표시됨
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.playCartoon:
                // cartoon_quiz1 액티비티로 이동하는 코드 작성
                Intent cartoonIntent = new Intent(main_page.this, cartoon_quiz1.class);
                startActivity(cartoonIntent);
                return true;

            case R.id.playAnimal:
                // animal_quiz1 액티비티로 이동하는 코드 작성
                Intent animalIntent = new Intent(main_page.this, animal_quiz1.class);
                startActivity(animalIntent);
                return true;

            case R.id.history:
                // history 액티비티로 이동하는 코드 작성
                Intent historyIntent = new Intent(main_page.this, history.class);
                startActivity(historyIntent);
                return true;

            case R.id.changePasswordMenu:
                // change_password 액티비티로 이동하는 코드 작성
                Intent changePasswordIntent = new Intent(main_page.this, change_password.class);
                startActivity(changePasswordIntent);
                return true;

            case R.id.instruction:
                // instruction 액티비티로 이동하는 코드 작성
                Intent instructionIntent = new Intent(main_page.this, instruction.class);
                startActivity(instructionIntent);
                return true;

            default:
                return false;
        }
    }
}
