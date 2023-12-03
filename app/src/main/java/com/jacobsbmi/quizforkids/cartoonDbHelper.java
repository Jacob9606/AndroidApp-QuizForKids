package com.jacobsbmi.quizforkids;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class cartoonDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cartoons.db";
    private static final int DATABASE_VERSION = 1;
    private List<Integer> usedQuestions;

    public cartoonDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        usedQuestions = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 질문 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS questions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "question_text TEXT," +
                "option1 TEXT," +
                "option2 TEXT," +
                "option3 TEXT," +
                "correct_answer INTEGER" +
                ")");

        // 예시 질문을 테이블에 삽입
        db.execSQL("INSERT INTO questions (question_text, option1, option2, option3, correct_answer) VALUES (" +
                "'Who is the main character of the animation Frozen?', 'Elsa', 'Anna', 'Olaf', 1)");
        // 추가 질문을 INSERT 문을 사용하여 삽입합니다.
        db.execSQL("INSERT INTO questions (question_text, option1, option2, option3, correct_answer) VALUES (" +
                "'Who is the main character of the animation Beauty and the Beast?', 'Belle', 'Beast', 'Mrs. Potts', 1)");
        db.execSQL("INSERT INTO questions (question_text, option1, option2, option3, correct_answer) VALUES (" +
                "'Who is the main character of the animation Inside Out?', 'Riley', 'Joy', 'Bing Bong', 1)");
        db.execSQL("INSERT INTO questions (question_text, option1, option2, option3, correct_answer) VALUES (" +
                "'Who is the main character of the animation Toy Story?', 'Woody', 'Slinky ', 'Timon', 1)");
        db.execSQL("INSERT INTO questions (question_text, option1, option2, option3, correct_answer) VALUES (" +
                "'Who is the main character of the animation The Lion King?', 'Simba', 'Nala', 'Timon', 1)");
        db.execSQL("INSERT INTO questions (question_text, option1, option2, option3, correct_answer) VALUES (" +
                "'Who is the main character of the animation Moana?', 'Maui', 'Heihei', 'Gramma Tala', 1)");
        db.execSQL("INSERT INTO questions (question_text, option1, option2, option3, correct_answer) VALUES (" +
                "'Who is the main character of the animation \"Cinderella\"?', 'CinderellaA', 'Fairy Godmother', 'Prince Charming', 1)");
        db.execSQL("INSERT INTO questions (question_text, option1, option2, option3, correct_answer) VALUES (" +
                "'Who is the main character of the animation \"Mulan\"?', 'Mushu', 'Li Shang', 'Grandmother Fa', 1)");
        db.execSQL("INSERT INTO questions (question_text, option1, option2, option3, correct_answer) VALUES (" +
                "'Who is the main character of the animation \"Aladdin\"?', 'Aladdin', 'Jasmine ', 'Genie', 1)");
        db.execSQL("INSERT INTO questions (question_text, option1, option2, option3, correct_answer) VALUES (" +
                "'Who is the main character of the animation \"Finding Nemo\"?', 'Nemo', 'Marlin', 'Dory', 1)");

        // 기존에 사용한 문제 ID 리스트를 초기화합니다.
        usedQuestions.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 필요한 경우 데이터베이스 업그레이드를 처리합니다.
    }

    public Cursor getRandomQuestion() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"question_text", "option1", "option2", "option3", "correct_answer"};
        String selection = "id NOT IN (" + getUsedQuestionIdsAsString() + ")";
        String orderBy = "RANDOM()";
        String limit = "1";
        Cursor cursor = db.query("questions", columns, selection, null, null, null, orderBy, limit);
        return cursor;
    }

    public String getUsedQuestionIdsAsString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < usedQuestions.size(); i++) {
            builder.append(usedQuestions.get(i));
            if (i < usedQuestions.size() - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }


    public void addUsedQuestionId(int questionId) {
        usedQuestions.add(questionId);
    }
}
