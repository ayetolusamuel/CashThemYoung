package com.setnumd.technologies.cashthemyoung;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.setnumd.technologies.cashthemyoung.constants.Quiz;
import com.setnumd.technologies.cashthemyoung.database.QuizRoomDatabase;
import com.setnumd.technologies.cashthemyoung.executor.AppExecutors;
import com.setnumd.technologies.cashthemyoung.network.NetworkStatus;
import com.setnumd.technologies.cashthemyoung.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.setnumd.technologies.cashthemyoung.constants.Quiz.ANSWER;
import static com.setnumd.technologies.cashthemyoung.constants.Quiz.HINTS;
import static com.setnumd.technologies.cashthemyoung.constants.Quiz.OPTION_A;
import static com.setnumd.technologies.cashthemyoung.constants.Quiz.OPTION_B;
import static com.setnumd.technologies.cashthemyoung.constants.Quiz.OPTION_C;
import static com.setnumd.technologies.cashthemyoung.constants.Quiz.OPTION_D;
import static com.setnumd.technologies.cashthemyoung.constants.Quiz.QUESTION;
import static com.setnumd.technologies.cashthemyoung.constants.Quiz.STAGE;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://raw.githubusercontent.com/ayetolusamuel/api_database_files/master/quiz.json";
    private static final String TAG = MainActivity.class.getSimpleName();
    private   String githubSearchResults;
    private List<Quiz> mQuizList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (NetworkStatus.getInstance(getApplicationContext()).isOnline()){
            Toast.makeText(this, "Network Yes", Toast.LENGTH_SHORT).show();
            fetchDataFromServer();
            queryData();
        }
        else {
            Toast.makeText(this, "Network No", Toast.LENGTH_SHORT).show();
            queryData();
        }


    }

    private void fetchDataFromServer() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    if (BASE_URL != null || !BASE_URL.isEmpty()){
                        URL githubUrl = new URL(BASE_URL);
                        githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubUrl);

                    }
         } catch (IOException e) {
                    e.printStackTrace();

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                final List<Quiz> quizList;
                        try {
                            quizList = readJsonData(githubSearchResults);
                            Log.d(TAG, "Size### "+quizList);
                            if (quizList != null)
                                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        QuizRoomDatabase.getInstance(getApplicationContext()).quizDao().insertToDatabase(quizList);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Toast.makeText(MainActivity.this, "The Size of Data from Server"+quizList.size(), Toast.LENGTH_SHORT).show();

                                            }
                                        });


                                    }
                                });


                        } catch (JSONException e) {
                            e.printStackTrace();
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
            String question,optA,optB,optC,optD,answer,stage,hints;

            question = menuItemObject.getString(QUESTION);
            optA = menuItemObject.getString(OPTION_A);
            optB = menuItemObject.getString(OPTION_B);
            optC = menuItemObject.getString(OPTION_C);
            optD = menuItemObject.getString(OPTION_D);
            answer = menuItemObject.getString(ANSWER);
            stage = menuItemObject.getString(STAGE);
            hints = menuItemObject.getString(HINTS);

            Quiz quiz = new Quiz(question,optA,optB,optC,optD,answer,stage,hints);

            quizzes.add(quiz);
        }

        return quizzes;
    }


    private void queryData(){
       AppExecutors.getInstance().diskIO().execute(new Runnable() {

            @Override
            public void run() {
               final List<Quiz> numOFQuestion = QuizRoomDatabase.getInstance(getApplicationContext()).quizDao().getQuiz();
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       if (numOFQuestion != null) {

                           mQuizList = numOFQuestion;
                           Toast.makeText(MainActivity.this, "List### "+mQuizList.size(), Toast.LENGTH_SHORT).show();


                       }else {
                           mQuizList = null;
                           Toast.makeText(MainActivity.this, "Erroe in fetching Data from database!!!", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
            }

        });

    }


}
