package com.setnumd.technologies.catchthemyoung;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.setnumd.technologies.cashthemyoung.R;
import com.setnumd.technologies.catchthemyoung.constants.Quiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Quiz> mQuizList;
    private RadioButton rbOptionA,rbOptionB,rbOptionC,rbOptionD;
    private TextView tvQuestion,tvHint,tvQuestionCount;
    private Quiz currentQuestion;
    private int index = 0;
    private int score;
    private Button mButtonNext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayData();
        Intent intent = getIntent();
        if (intent.hasExtra("initial_count")) {

            index = intent.getIntExtra("initial_count", 0);
            Log.d(TAG, "onCreate: index from result " + index);
        }
    }

    private void displayData() {
        fetchDataFromServer();
        queryData();

    }

    private List<Quiz> fetchDataFromServer() {
        List<Quiz> quizList = null;
        try {
            quizList = readJsonData(loadJSONFromAsset());

        } catch (JSONException e) {
            e.printStackTrace();
        }
       return quizList;

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("quiz.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }




    private List<Quiz> readJsonData(String jsonSting) throws JSONException {


        JSONObject json = new JSONObject(jsonSting);
        JSONArray jsonArray = json.getJSONArray("quiz");

        int length = jsonArray.length();
        List<Quiz> quizzes = new ArrayList<>();

        for (int i = 0; i < length; i++) {

            JSONObject menuItemObject = jsonArray.getJSONObject(i);
            String question, optA, optB, optC, optD, answer, stage, hints;

            question = menuItemObject.getString(Quiz.QUESTION);
            optA = menuItemObject.getString(Quiz.OPTION_A);
            optB = menuItemObject.getString(Quiz.OPTION_B);
            optC = menuItemObject.getString(Quiz.OPTION_C);
            optD = menuItemObject.getString(Quiz.OPTION_D);
            answer = menuItemObject.getString(Quiz.ANSWER);
            stage = menuItemObject.getString(Quiz.STAGE);
            hints = menuItemObject.getString(Quiz.HINTS);

            Quiz quiz = new Quiz(question, optA, optB, optC, optD, answer, stage, hints);
            quizzes.add(quiz);

        }

        return quizzes;
    }


    private void queryData() {
              final List<Quiz> numOFQuestion = fetchDataFromServer();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (numOFQuestion != null) {
                            mQuizList = numOFQuestion;
                            Collections.shuffle(mQuizList);
                            currentQuestion = mQuizList.get(index);
                            attachData(mQuizList);

                        }
                    }
                });
            }

    private void attachData(List<Quiz> quizlist){

        if (quizlist != null){

            rbOptionA = findViewById(R.id.rbOptionA);
            rbOptionB = findViewById(R.id.rbOptionB);
            rbOptionC = findViewById(R.id.rbOptionC);
            rbOptionD = findViewById(R.id.rbOptionD);

            tvHint = findViewById(R.id.hint);
            tvHint.setVisibility(View.INVISIBLE);
            tvQuestion = findViewById(R.id.tvQuestion);
            tvQuestionCount = findViewById(R.id.questionCount);


            mButtonNext = findViewById(R.id.next_button);
            mButtonNext.setVisibility(View.INVISIBLE);


           Quiz quiz = quizlist.get(index);
           tvQuestion.setText(quiz.getQuestion());
           rbOptionA.setText(quiz.getOptionA());
           rbOptionB.setText(quiz.getOptionB());
           rbOptionC.setText(quiz.getOptionC());
           rbOptionD.setText(quiz.getOptionD());
           tvHint.setText("Hint : "+quiz.getHints());

           tvQuestionCount.setText(index+"/"+"20");

           index++;
        }


    }

    public void nextButtonClicked(View view) {
       if (mQuizList != null)
           enableRadioButton();
       currentQuestion = mQuizList.get(index);
        attachData(mQuizList);

    }


    public void radioButtonClicked(View view){
       if (index<= 20){
            RadioGroup radioGroup = findViewById(R.id.rGroup);
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            if (currentQuestion.getAnswer().equals(radioButton.getText())){
               radioGroup.clearCheck();
                disableRadioButton();
                tvHint.setVisibility(View.VISIBLE);
                score = score + 2;
                Toast.makeText(this, "Correct!!! " +score, Toast.LENGTH_SHORT).show();


            }
            else {
                Toast.makeText(this, "Incorrect Answer!!!", Toast.LENGTH_SHORT).show();
                radioGroup.clearCheck();
                disableRadioButton();
                tvHint.setVisibility(View.VISIBLE);
            }
            mButtonNext.setVisibility(View.VISIBLE);

        }else{
           Intent intent = new Intent(this,ResultActivity.class);
            intent.putExtra("score",score);
            startActivity(intent);
            finish();
        }
       


    }

    private void disableRadioButton() {
        rbOptionA.setEnabled(false);
        rbOptionB.setEnabled(false);
        rbOptionC.setEnabled(false);
        rbOptionD.setEnabled(false);
    }

    private void enableRadioButton(){
        rbOptionA.setEnabled(true);
        rbOptionB.setEnabled(true);
        rbOptionC.setEnabled(true);
        rbOptionD.setEnabled(true);

}

}