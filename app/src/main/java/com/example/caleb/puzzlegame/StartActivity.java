package com.example.caleb.puzzlegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by Caleb on 2/23/2018.
 */

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);





    }
    //onclick for the button
    //moves to the next activity
    public void Play(View view){
        startActivity(new Intent(StartActivity.this, MainActivity.class));

    }
}
