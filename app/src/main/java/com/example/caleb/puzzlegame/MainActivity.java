package com.example.caleb.puzzlegame;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/*********************************************************************************
 * Caleb Damler
 * CSCI 428
 * Assign 2
 * 3/5/2018
 *
 * this app is a puzzle game that allows the user to scramble a word then try to solve it
 * the use can give up and solve the puzzle by pressing a button, as well as start a new puzzle
 * with a new word
 *********************************************************************************/
public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    public static int STATUS_BAR_HEIGHT = 24; //dp
    public static int ACTION_BAR_HEIGHT = 56; //dp
    private Puzzle puzzle;
    private PuzzleView pv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        puzzle = new Puzzle();
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y-100;
        int puzzleWidth = size.x;
        Resources res = getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        float pixDens = metrics.density;
        int actionBarHeight = (int)(pixDens*ACTION_BAR_HEIGHT);
        TypedValue typedValue = new TypedValue();
        if(getTheme().resolveAttribute(android.R.attr.actionBarSize,typedValue,true)){
            actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data,metrics);
        }
        int statusBarHeight = (int)(pixDens*STATUS_BAR_HEIGHT);
        int resID = res.getIdentifier("status_bar_height","dimen","android");
        if (resID != 0){
            statusBarHeight = res.getDimensionPixelOffset(resID);
        }
        int puzzleHeight = screenHeight - statusBarHeight - actionBarHeight;
        pv = new PuzzleView(this, puzzleWidth, puzzleHeight,puzzle.getNumOarts());
        final String[] scrambled = puzzle.scramble();
        pv.fillGui(scrambled);


        pv.endableListener(this);

        //add two buttons for quitting and solving the puzzle
        Button button1 = new Button(this);
        Button button2 = new Button(this);
        button1.setText("Quit");
        button2.setText("Solve");
        //button onClick listeners
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPuzzle();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solvePuzzle();

            }
        });
        //button1 setup
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_RIGHT);
        params.setMargins(0,0,0,50);
        pv.addView(button1, params);
        //button2 setup
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params2.addRule(RelativeLayout.ALIGN_LEFT);
        params.setMargins(830,0,0,0);
        pv.addView(button2,params2);


        setContentView(pv);

    }

    /**********************************************************
     * newPuzzle
     *
     * quits the game
     **********************************************************/
    private void newPuzzle() {
        startActivity(new Intent(MainActivity.this, StartActivity.class));
    }
    /**********************************************************
     * solvePuzzle
     *
     *
     * solves the current puzzle
     **********************************************************/
    private void solvePuzzle() {
        String[] solved = puzzle.getSolution();
        pv.fillGui(solved);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int index = pv.indexOfText(view);
        int action = motionEvent.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                pv.updateStartPositions(index,(int)motionEvent.getY());
                pv.bringChildToFront(view);
                break;
            case MotionEvent.ACTION_MOVE:
                pv.moveTextViewVertically(index,(int)motionEvent.getY());
                break;
            case MotionEvent.ACTION_UP:
                int newPos = pv.tvsPosition(index);
                pv.placeTextViewAtPos(index, newPos);
                if(puzzle.solved(pv.currentSol())){
                    pv.disableListener();
                }
        }
        return true;
    }
}
