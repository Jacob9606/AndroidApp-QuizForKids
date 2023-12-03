package com.jacobsbmi.quizforkids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.content.res.Resources;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Field;

public class animal_quiz1 extends AppCompatActivity {
    private static int CorrectAnswer = 0;
    private static int WrongAnswer = 0;
    private Button confirmButton;
    private int resourceId;
    private EditText guessedAnswer;
    private AnimalImageDbHelper mDbHelper;
    private ImageView randomAnimalImg;

    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_quiz1);

        // Get the resources object
        Resources resources = getResources();

        // Get the package name
        String packageName = getPackageName();

        // Get the resource ID for all drawable files
        Field[] drawableFields = R.drawable.class.getDeclaredFields();
        for (Field field : drawableFields) {
            try {
                // Get the resource ID using the field name
                int resourceId = resources.getIdentifier(field.getName(), "drawable", packageName);
                // Use the resourceId as needed
                Log.d("Drawable resource ID: "+field.getName() , String.valueOf(resourceId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        nextButton = findViewById(R.id.nextButton);

        guessedAnswer = findViewById(R.id.guessedAnswer);

        // Initialize confirmButton
        confirmButton = findViewById(R.id.confirmButton);

        // database helper 생성
        mDbHelper = new AnimalImageDbHelper(this);

        // ImageView 초기화 및 랜덤 동물 이미지 설정
        randomAnimalImg = findViewById(R.id.randomAnimalImg);
        setRandomAnimalImage();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(animal_quiz1.this, animal_quiz2.class);
                startActivity(intent);
                finish();

            }
        });

        // "confirmButton" 버튼 클릭시 액티비티 이동
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // guessedAnswer의 텍스트 가져오기
                String answer = guessedAnswer.getText().toString().trim();

                // resourceId로부터 파일명 가져오기
                String filename = getResourceName(resourceId);

                // 정답 비교
                if(answer.equals("")){
                    Toast.makeText(animal_quiz1.this, "You are Wrong! Wrong answers: " + (++WrongAnswer), Toast.LENGTH_SHORT).show();
                    nextquestion();
                }else if (answer.equals(filename)) {
                    // 정답인 경우
                    Toast.makeText(animal_quiz1.this, "You are correct! Correct answers: " + (++CorrectAnswer), Toast.LENGTH_SHORT).show();
                    nextquestion();
                } else {
                    // 오답인 경우
                    Toast.makeText(animal_quiz1.this, "You are Wrong! Wrong answers: " + (++WrongAnswer), Toast.LENGTH_SHORT).show();
                    nextquestion();
                }

                // guessedAnswer 초기화
                guessedAnswer.setText("");

                // animal_quiz2 액티비티로 이동


            }
        });

        // 액션바를 설정합니다. 상단에 위치한 보라색 바
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }



    public boolean onCreateOptionsMenu(Menu menu){
        // 메뉴를 확장하여 액션바에 아이템을 추가합니다.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true; //true를 리턴하면 메뉴가 액션바에 표시됨
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.playCartoon:
                // cartoon_quiz1 액티비티로 이동하는 코드 작성
                Intent cartoonIntent = new Intent(animal_quiz1.this, cartoon_quiz1.class);
                startActivity(cartoonIntent);
                return true;

            case R.id.playAnimal:
                // animal_quiz1 액티비티로 이동하는 코드 작성
                Intent animalIntent = new Intent(animal_quiz1.this, animal_quiz1.class);
                startActivity(animalIntent);
                return true;

            case R.id.history:
                // history 액티비티로 이동하는 코드 작성
                Intent historyIntent = new Intent(animal_quiz1.this, history.class);
                startActivity(historyIntent);
                return true;

            case R.id.changePasswordMenu:
                // change_password 액티비티로 이동하는 코드 작성
                Intent changePasswordIntent = new Intent(animal_quiz1.this, change_password.class);
                startActivity(changePasswordIntent);
                return true;

            case R.id.instruction:
                // instruction 액티비티로 이동하는 코드 작성
                Intent instructionIntent = new Intent(animal_quiz1.this, instruction.class);
                startActivity(instructionIntent);
                return true;

            default:
                return false;
        }
    }

    private void nextquestion(){
        Intent intent = new Intent(animal_quiz1.this, animal_quiz2.class);
        intent.putExtra("CorrectAnswer", CorrectAnswer);
        intent.putExtra("WrongAnswer", WrongAnswer);
        finish();
        startActivity(intent);
    }

    // resourceId로부터 파일명 가져오는 함수
    private String getResourceName(int resourceId) {
        if (resourceId == 0) {
            return "";
        }
        Resources res = getResources();
        String resName = res.getResourceName(resourceId);
        return resName.substring(resName.lastIndexOf("/") + 1);
    }

    private void setRandomAnimalImage() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                AnimalImageContract.AnimalImageEntry.COLUMN_NAME_FILENAME
        };
        String sortOrder = "RANDOM()";
        Cursor cursor = db.query(
                AnimalImageContract.AnimalImageEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder,
                "1"
        );
        if (cursor.moveToNext()) {
            String filename = cursor.getString(cursor.getColumnIndexOrThrow(AnimalImageContract.AnimalImageEntry.COLUMN_NAME_FILENAME));
            resourceId = getResources().getIdentifier(filename, "drawable", getPackageName());

            Log.d("RESOURCE Q1", String.valueOf(resourceId));
            // Get an instance of SharedPreferences.Editor
            SharedPreferences sharedPreferences = this.getSharedPreferences("ResourceID", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("ID 1", resourceId);
            editor.apply();
            randomAnimalImg.setImageResource(resourceId);
        }
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}
