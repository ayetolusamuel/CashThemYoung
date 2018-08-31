package com.setnumd.technologies.catchthemyoung;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.setnumd.technologies.cashthemyoung.R;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle b = getIntent().getExtras();
        TextView scoreTxtView = (TextView) findViewById(R.id.score);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar1);
        ImageView img = (ImageView)findViewById(R.id.img1);


        if (b != null){
            int score = b.getInt("score");
            int ratingScore = score/8;

            ratingBar.setRating(ratingScore);
            scoreTxtView.setText(String.valueOf(score));

            TextView scoreLine = findViewById(R.id.scoreLine);
            scoreLine.setText("Score Line : "+score+"/"+"40");

            if(score >=0 && score<10){
                img.setBackgroundResource(R.drawable.score_0);
              //  img.setImageResource(R.drawable.score_0);
            }else if(score >10 && score<20){
                img.setBackgroundResource(R.drawable.score_2);
               // img.setImageResource(R.drawable.score_1);
            }else if(score >20 && score<=24){
                img.setBackgroundResource(R.drawable.score_3);
               // img.setImageResource(R.drawable.score_2);
            }else if(score >25 && score <30){
                //img.setImageResource(R.drawable.score_3);
                img.setBackgroundResource(R.drawable.score_4);
            }else if(score >30 && score <=40){
             //   img.setImageResource(R.drawable.score_5);
                img.setBackgroundResource(R.drawable.score_5);
            }
        }


        }




}
