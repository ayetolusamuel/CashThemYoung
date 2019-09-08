package com.setnumd.technologies.catchthemyoung;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.setnumd.technologies.cashthemyoung.R;

public class ResultActivity extends AppCompatActivity {
    int score;
    Button button;
    MainActivity mMainActivity;
    private static final String TAG = "ResultActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        button= findViewById(R.id.playAgainButton);


        Bundle b = getIntent().getExtras();
        TextView scoreTxtView = (TextView) findViewById(R.id.score);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar1);
        ImageView img = (ImageView)findViewById(R.id.img1);

//        CapitaliseName capitaliseName = new CapitaliseName() {
//            @Override
//            public void toUppercase(int index) {
//               getIndex();
//
//            }
//        };
//
//        mMainActivity.setOnChangeLister(capitaliseName);
//



       // int score;
        if (b != null){
             score = b.getInt("score");
            System.out.println("Result score "+score);
            int ratingScore = score/8;

            ratingBar.setRating(ratingScore);
            scoreTxtView.setText(String.valueOf(score));

            TextView scoreLine = findViewById(R.id.scoreLine);
            scoreLine.setText("Score Line : "+score+"/"+"40");

            if(score >=0 && score<=10){
                img.setBackgroundResource(R.drawable.sad);
              //  img.setImageResource(R.drawable.score_0);
            }
            else if(score >10 && score<=20){
                img.setBackgroundResource(R.drawable.fair);
               // img.setImageResource(R.drawable.score_1);
            }else if(score >20 && score<=30){
                img.setBackgroundResource(R.drawable.happy);
               // img.setImageResource(R.drawable.score_2);
            }else if(score >30 && score <=40){
                //img.setImageResource(R.drawable.score_3);
                img.setBackgroundResource(R.drawable.excellence);
            }
        }
        buttonCheck(score);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        buttonActionPerform(score);
            }
        });

        }


//        private void getIndex(){
//       int index =  mMainActivity.getIndex();
//            Log.d(TAG, "getIndex: "+index);
//        }

    private void buttonCheck(int score) {
        if (score>38){
            button.setText("Next Stage!");

        }
        else{
            button.setText("Play Again!");


        }
    }

    private void buttonActionPerform(int score){
            System.out.println("scores "+score);
        if (score>38){
            button.setText("Next Stage!");
            Toast.makeText(this, "Congrat you can move to next Stage!", Toast.LENGTH_SHORT).show();

        }
        else{
            button.setText("Play Again!");
            Toast.makeText(this, "You are not quality, score above 38", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
           intent.putExtra("initial_count",0);
            startActivity(intent);

            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            finish();
            //startActivity(new Intent(ResultActivity.this, MainActivity.class));
            //finish();
        }
        }


//
//
//ratingBar.setEnabled(false);
//ratingBar.setMax(5);
//ratingBar.setStepSize(0.01f);
//ratingBar.setRating(Float.parseFloat(stringrate));
//ratingBar.invalidate();

}
