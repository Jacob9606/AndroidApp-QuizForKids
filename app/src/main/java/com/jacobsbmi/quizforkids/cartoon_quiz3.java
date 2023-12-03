package com.jacobsbmi.quizforkids;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.widget.Toast;
import android.content.Intent;

public class cartoon_quiz3 extends AppCompatActivity {
    private TextView question3ContentTxt;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button nextButton;
    private Button previousButton;
    private int Answer = 0;
    private int wrongAnswer = 0;

    private cartoonDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartoon_quiz3);

        question3ContentTxt = findViewById(R.id.question3ContentTxt);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);


        // cartoonDbHelper 인스턴스 생성
        dbHelper = new cartoonDbHelper(this);

        // 랜덤한 질문 가져오기
        Cursor cursor = dbHelper.getRandomQuestion();

        if (cursor.moveToFirst()) {
            int questionIndex = cursor.getColumnIndex("question_text");
            int option1Index = cursor.getColumnIndex("option1");
            int option2Index = cursor.getColumnIndex("option2");
            int option3Index = cursor.getColumnIndex("option3");
            int correctAnswerIndex = cursor.getColumnIndex("correct_answer");

            if (questionIndex >= 0 && option1Index >= 0 && option2Index >= 0 && option3Index >= 0 && correctAnswerIndex >= 0) {
                String question = cursor.getString(questionIndex);
                String option1Text = cursor.getString(option1Index);
                String option2Text = cursor.getString(option2Index);
                String option3Text = cursor.getString(option3Index);
                Answer = cursor.getInt(correctAnswerIndex);

                // 가져온 질문과 선택지를 화면에 표시
                question3ContentTxt.setText(question);
                option1.setText(option1Text);
                option2.setText(option2Text);
                option3.setText(option3Text);
            }
        }

        cursor.close();

        // 선택지 버튼 클릭 이벤트 처리
        option1.setOnClickListener(v -> checkAnswer(1));
        option2.setOnClickListener(v -> checkAnswer(2));
        option3.setOnClickListener(v -> checkAnswer(3));
        // 다음 버튼 클릭 이벤트 처리
        nextButton.setOnClickListener(v -> moveToNextQuestion());
        // 이전 버튼 클릭 이벤트 처리
        previousButton.setOnClickListener(v -> moveToPreviousQuestion());

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
                Intent cartoonIntent = new Intent(cartoon_quiz3.this, cartoon_quiz1.class);
                startActivity(cartoonIntent);
                return true;

            case R.id.playAnimal:
                // animal_quiz1 액티비티로 이동하는 코드 작성
                Intent animalIntent = new Intent(cartoon_quiz3.this, animal_quiz1.class);
                startActivity(animalIntent);
                return true;

            case R.id.history:
                // history 액티비티로 이동하는 코드 작성
                Intent historyIntent = new Intent(cartoon_quiz3.this, history.class);
                startActivity(historyIntent);
                return true;

            case R.id.changePasswordMenu:
                // change_password 액티비티로 이동하는 코드 작성
                Intent changePasswordIntent = new Intent(cartoon_quiz3.this, change_password.class);
                startActivity(changePasswordIntent);
                return true;

            case R.id.instruction:
                // instruction 액티비티로 이동하는 코드 작성
                Intent instructionIntent = new Intent(cartoon_quiz3.this, instruction.class);
                startActivity(instructionIntent);
                return true;

            default:
                return false;
        }
    }

    // 답 확인 및 처리
    private void checkAnswer(int selectedOption) {
        Intent intent = getIntent();
        int correctAnswer = intent.getIntExtra("CorrectAnswer",0);
        int wrongAnswer = intent.getIntExtra("WrongAnswer", 0);


        if (selectedOption == Answer) {
            // 정답 처리
            correctAnswer++;
            Toast.makeText(this, "You are correct! Correct Answers: " + correctAnswer, Toast.LENGTH_SHORT).show();
            dbHelper.addUsedQuestionId(Answer - 1);

            // cartoon_quiz3 액티비티로 이동
            next(correctAnswer,wrongAnswer);

        } else {
            // 오답 처리
            wrongAnswer++;
            Toast.makeText(this, "You are Wrong! Wrong Answers: " + wrongAnswer, Toast.LENGTH_SHORT).show();
            dbHelper.addUsedQuestionId(Answer - 1);

            // cartoon_quiz3 액티비티로 이동
            next(correctAnswer,wrongAnswer);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // dbHelper 사용이 끝났으므로 리소스 해제
        dbHelper.close();
    }


    private void next(int correctAnswer,int wrongAnswer){
        Intent intent = new Intent(this, cartoon_quiz4.class);
        intent.putExtra("CorrectAnswer", correctAnswer);
        intent.putExtra("WrongAnswer", wrongAnswer);
        finish();
        startActivity(intent);
    }

    private void moveToNextQuestion() {
        // cartoon_quiz4 액티비티로 이동
        Intent intent = new Intent(this, cartoon_quiz4.class);
        startActivity(intent);
    }
    private void moveToPreviousQuestion () {
        // cartoon_quiz1 액티비티로 이동
        Intent intent = new Intent(this, cartoon_quiz2.class);
        startActivity(intent);
    }

}
