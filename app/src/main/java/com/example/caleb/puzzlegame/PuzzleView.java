package com.example.caleb.puzzlegame;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Caleb on 1/31/2018.
 */

public class PuzzleView extends RelativeLayout {


    private TextView [] tvs;
    private RelativeLayout.LayoutParams [] params;
    private int [] colors;
    private int  labelHeight;
    private int startY, startTouchY;
    private int emptyPos;
    private int [] positions;

    public PuzzleView(Activity activity, int width, int height, int numberPieces){
        super(activity);
        buildGuiByCode(activity, width, height, numberPieces);
    }

    private void buildGuiByCode(Activity activity, int width, int height, int numberPieces) {
        tvs = new TextView[numberPieces];
        positions = new int[numberPieces];
        colors = new int[tvs.length];
        params = new RelativeLayout.LayoutParams[tvs.length];
        Random random = new Random();
        labelHeight = (height)/numberPieces;

        for(int i = 0; i<tvs.length; i++){
            tvs[i] = new TextView(activity);
            tvs[i].setGravity(Gravity.CENTER);
            colors[i] = Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255));
            tvs[i].setBackgroundColor(colors[i]);

            params[i] = new RelativeLayout.LayoutParams(width,labelHeight);
            params[i].leftMargin = 0;
            params[i].topMargin = labelHeight * i;


            addView(tvs[i],params[i]);

        }






    }

    public void fillGui(String[] scrambledText){
        for(int i = 0; i<scrambledText.length; i++){
            tvs[i].setText(scrambledText[i]);
            positions[i] = i;
        }
    }

    public int indexOfText(View tv){
        if(!(tv instanceof TextView)){
            return -1;
        }
        for(int i=0; i<tvs.length; i++){
            if(tv == tvs[i])
                return i;
        }
        return -1;
    }

    public void updateStartPositions(int index, int y){
        startY = params[index].topMargin;
        startTouchY = y;
        emptyPos = tvsPosition(index);
    }

    public int tvsPosition(int tvIndex){
        return (params[tvIndex].topMargin + labelHeight / 2) / labelHeight;
    }

    public void moveTextViewVertically(int index, int y){
        params[index].topMargin = startY + y + startTouchY;
        tvs[index].setLayoutParams(params[index]);
    }
    public void endableListener(View.OnTouchListener listener){
        for(int i= 0; i<tvs.length; i++)
            tvs[i].setOnTouchListener(listener);

    }


    public void disableListener(){
        for(int i = 0; i<tvs.length; i++){
            tvs[i].setOnTouchListener(null);
        }
    }

    public void placeTextViewAtPos(int tvIndex, int toPostion){

        params[tvIndex].topMargin = toPostion * labelHeight;
        tvs[tvIndex].setLayoutParams(params[tvIndex]);
        //move the position to the one we just moved
        int index = positions[toPostion];
        params[index].topMargin = emptyPos * labelHeight;
        tvs[index].setLayoutParams(params[index]);
        //reset pos
        positions[emptyPos] = index;
        positions[toPostion] = tvIndex;
    }

    public String[] currentSol(){
        String [] current = new String[tvs.length];
        for(int i = 0; i<tvs.length; i ++){
            current[i] = tvs[positions[i]].getText().toString();
        }
        return current;
    }







}
