package com.setnumd.technologies.catchthemyoung;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.setnumd.technologies.cashthemyoung.R;
import com.setnumd.technologies.catchthemyoung.constants.Quiz;
import com.setnumd.technologies.catchthemyoung.database.QuizRoomDatabase;
import com.setnumd.technologies.catchthemyoung.executor.AppExecutors;
import com.setnumd.technologies.catchthemyoung.network.NetworkStatus;
import com.setnumd.technologies.catchthemyoung.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://raw.githubusercontent.com/ayetolusamuel/api_database_files/master/quiz.json";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static Context context;
    private   String githubSearchResults;
    private List<Quiz> mQuizList;
    private RadioButton rbOptionA,rbOptionB,rbOptionC,rbOptionD;
    private TextView tvQuestion,tvHint,tvQuestionCount;
    private ProgressBar progressBar;
    private Quiz currentQuestion;
    private static int index = 0;
    private int score = 2;
    private Button mButtonNext;
    private static int databaseCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        tvOptionA = findViewById(R.id.tvOptionA);
//        tvOptionB = findViewById(R.id.tvOptionB);
//        tvOptionC = findViewById(R.id.tvOptionC);
//        tvOptionD = findViewById(R.id.tvOptionD);

        displayData();


        progressBar = findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);




       /* if (NetworkStatus.getInstance(getApplicationContext()).isOnline()){
           // Toast.makeText(this, "Network Yes", Toast.LENGTH_SHORT).show();
            fetchDataFromServer();
            queryData();

        }
        else {
           // Toast.makeText(this, "Network No", Toast.LENGTH_SHORT).show();
            queryData();

        }*/



    }

    private void displayData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                long aLong = QuizRoomDatabase.getInstance(getApplicationContext()).quizDao().getDatabaseCount();
                float aFloat = (float)aLong;
                final int aInt = (int)(aFloat);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int database = aInt;
             //System.out.println("database# "+database);  // databasecount = 0 mean no data in  database
                        if (database == 0){


               if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                   fetchDataFromServer();
               }else{
                   Toast.makeText(MainActivity.this, "No Network, check network!!", Toast.LENGTH_SHORT).show();
               }

                        }else{
                            queryData();
                        }
                    }
                });
            }
        });
    }

    private void fetchDataFromServer() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    if (BASE_URL != null || !BASE_URL.isEmpty()) {
                        URL githubUrl = new URL(BASE_URL);
                        githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubUrl);


                    }
                } catch (IOException e) {
                    e.printStackTrace();

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (githubSearchResults != null){
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    List<Quiz> quizList = null;
                                    try {
                                        quizList = readJsonData(githubSearchResults);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (quizList != null)
                                       // QuizRoomDatabase.getInstance(getApplicationContext()).quizDao().deleteQuiz();
                                        System.out.println("LISTME#### "+quizList.size());

                                    System.out.println("list from database "+databaseCount);
                                    System.out.println("List from server "+quizList.size());

                                    for (Quiz quiz :quizList){

                                        QuizRoomDatabase.getInstance(getApplicationContext()).quizDao().insertToDatabase(quiz);

                                    }

                                }
                            });
                             }

                    }
                });


            }





        });


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
           // System.out.println("PLS###### "+quizzes.size());
        }

        return quizzes;
    }


    private void queryData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {

            @Override
            public void run() {
                final List<Quiz> numOFQuestion = QuizRoomDatabase.getInstance(getApplicationContext()).quizDao().getQuiz();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (numOFQuestion != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                            mQuizList = numOFQuestion;
                            Collections.shuffle(mQuizList);
                           // Log.d(TAG,"radio index "+index);
                            currentQuestion = mQuizList.get(index);
                           // Log.d(TAG,"radio Check"+currentQuestion.getAnswer());
                            attachData(mQuizList);

                        } else {
                            mQuizList = null;
                            Toast.makeText(MainActivity.this, "Erroe in fetching Data from database!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
       // index++;
    }


    public void radioButtonClicked(View view){
       // boolean checked = ((RadioButton) view).isChecked();

       // currentQuestion = mQuizList.get(index);
        if (index<= 20){
            RadioGroup radioGroup = findViewById(R.id.rGroup);
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
          //  Log.d(TAG, "radioButtonClicked:Answer  "+currentQuestion.getAnswer());

          //  Log.d(TAG,"radioButtonClicked: "+radioButton.getText());

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
          //  Toast.makeText(this, "Thanks for playing!!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ResultActivity.class);
            intent.putExtra("score",score);
            startActivity(intent);
            finish();
//            Bundle b = new Bundle();
//            b.putInt("score",score);
//            intent.putExtras(b);
//            startActivity(intent);
//            finish();
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

    @Override
    protected void onStart() {
        super.onStart();
//        index = 0;
//        System.out.println("Samuel");
//        displayData();
    }
}