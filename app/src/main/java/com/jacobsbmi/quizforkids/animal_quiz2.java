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

public class animal_quiz2 extends AppCompatActivity {

    private static int WrongAnswer = 0;
    private Button confirmButton;
    private Button nextButton;
    private Button previousButton;
    private int resourceId;
    private EditText guessedAnswer;
    private AnimalImageDbHelper mDbHelper;
    private ImageView randomAnimalImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_quiz2);

        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);
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
                Intent intent = new Intent(animal_quiz2.this, animal_quiz3.class);
                startActivity(intent);
                finish();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(animal_quiz2.this, animal_quiz1.class);
                startActivity(intent);
                finish();
            }
        });

        // "confirmButton" 버튼 클릭시 액티비티 이동
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                int CorrectAnswer = intent.getIntExtra("CorrectAnswer",0);
                int WrongAnswer = intent.getIntExtra("WrongAnswer", 0);
                // guessedAnswer의 텍스트 가져오기
                String answer = guessedAnswer.getText().toString().trim();

                // resourceId로부터 파일명 가져오기
                String filename = getResourceName(resourceId);

                // 정답 비교
                if(answer.equals("")){
                    // 오답인 경우
                    Toast.makeText(animal_quiz2.this, "You are Wrong! Wrong answers: "  + (++WrongAnswer), Toast.LENGTH_SHORT).show();
                    nextquestion(CorrectAnswer,WrongAnswer);
                }else if (answer.equals(filename)) {
                    // 정답인 경우
                    Toast.makeText(animal_quiz2.this, "You are correct! Correct answers: " + (++CorrectAnswer), Toast.LENGTH_SHORT).show();
                    nextquestion(CorrectAnswer,WrongAnswer);
                } else {
                    // 오답인 경우
                    Toast.makeText(animal_quiz2.this, "You are Wrong! Wrong answers: "  + (++WrongAnswer), Toast.LENGTH_SHORT).show();
                    nextquestion(CorrectAnswer,WrongAnswer);
                }

                // guessedAnswer 초기화
                guessedAnswer.setText("");


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
                Intent cartoonIntent = new Intent(animal_quiz2.this, cartoon_quiz1.class);
                startActivity(cartoonIntent);
                return true;

            case R.id.playAnimal:
                // animal_quiz1 액티비티로 이동하는 코드 작성
                Intent animalIntent = new Intent(animal_quiz2.this, animal_quiz1.class);
                startActivity(animalIntent);
                return true;

            case R.id.history:
                // history 액티비티로 이동하는 코드 작성
                Intent historyIntent = new Intent(animal_quiz2.this, history.class);
                startActivity(historyIntent);
                return true;

            case R.id.changePasswordMenu:
                // change_password 액티비티로 이동하는 코드 작성
                Intent changePasswordIntent = new Intent(animal_quiz2.this, change_password.class);
                startActivity(changePasswordIntent);
                return true;

            case R.id.instruction:
                // instruction 액티비티로 이동하는 코드 작성
                Intent instructionIntent = new Intent(animal_quiz2.this, instruction.class);
                startActivity(instructionIntent);
                return true;

            default:
                return false;
        }
    }
    private void nextquestion(int CorrectAnswer,int WrongAnswer){
        // animal_quiz2 액티비티로 이동
        Intent intent = new Intent(animal_quiz2.this, animal_quiz3.class);
        intent.putExtra("CorrectAnswer", CorrectAnswer);
        intent.putExtra("WrongAnswer", WrongAnswer);
        finish();
        startActivity(intent);
    }

    // resourceId로부터 파일명 가져오는 함수
    private String getResourceName(int resourceId) {
        if (resourceId == 0) {

            Log.d("IF Executed ","");
            return "";
        }

        Log.d("CHECK ID ", String.valueOf(resourceId));
        Resources res = getResources();
        String resName = res.getResourceName(resourceId);
        Log.d("QUIZ 2 "+resName , String.valueOf(resourceId));

        return resName.substring(resName.lastIndexOf("/") + 1);
    }

    private void setRandomAnimalImage() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                AnimalImageContract.AnimalImageEntry.COLUMN_NAME_FILENAME
        };

        // animal_quiz1에서 사용된 이미지 파일명 가져오기
        String[] usedFilenames = getUsedFilenames();

        // 사용된 이미지를 제외한 이미지를 가져오기 위한 WHERE 조건 설정
        String selection = AnimalImageContract.AnimalImageEntry.COLUMN_NAME_FILENAME + " NOT IN (" + getPlaceholders(usedFilenames.length) + ")";

        // WHERE 조건에 사용할 값 설정
        String[] selectionArgs = usedFilenames;

        Cursor cursor = db.query(
                AnimalImageContract.AnimalImageEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                "RANDOM()",
                "1"
        );

        if (cursor.moveToNext()) {
            String filename = cursor.getString(cursor.getColumnIndexOrThrow(AnimalImageContract.AnimalImageEntry.COLUMN_NAME_FILENAME));
            resourceId = getResources().getIdentifier(filename, "drawable", getPackageName());
            SharedPreferences sharedPreferences = this.getSharedPreferences("ResourceID", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("ID 2", resourceId);
            editor.apply();

            randomAnimalImg.setImageResource(resourceId);
        }
        cursor.close();
    }

    // animal_quiz1에서 사용된 이미지 파일명을 가져오는 함수
    private String[] getUsedFilenames() {


        Intent intent = getIntent();
        int CorrectAnswer = intent.getIntExtra("CorrectAnswer",0);
        int WrongAnswer = intent.getIntExtra("WrongAnswer", 0);

        String[] usedFilenames = new String[1];

        // animal_quiz1에서 사용된 이미지 파일명을 배열에 저장
        SharedPreferences sharedPreferences = this.getSharedPreferences("ResourceID", Context.MODE_PRIVATE);
         int ID = sharedPreferences.getInt("ID 1", 0);

            usedFilenames[0] = getResourceName(ID);

        return usedFilenames;
    }

    // WHERE 조건에 사용할 플레이스홀더 문자열 생성 함수
    private String getPlaceholders(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append("?");
        }
        return builder.toString();
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}
