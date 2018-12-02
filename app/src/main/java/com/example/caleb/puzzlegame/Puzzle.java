package com.example.caleb.puzzlegame;

import java.util.Random;

/**
 * Created by Caleb on 1/29/2018.
 */

public class Puzzle {

    private String[] parts;
    private Random random = new Random();
    public static final int NUM_PARTS = 6;


    public Puzzle(){
        //generate a random number to choose the puzzle word 1-6
        Random rand = new Random();
        int n = rand.nextInt(5) + 1;
        parts = new String[NUM_PARTS];
        if(n == 0) {

            parts[0] = "O";
            parts[1] = "R";
            parts[2] = "A";
            parts[3] = "N";
            parts[4] = "G";
            parts[5] = "E";
        } else if(n == 1){
            parts[0] = "S";
            parts[1] = "C";
            parts[2] = "H";
            parts[3] = "O";
            parts[4] = "O";
            parts[5] = "L";

        }else if(n == 2){
            parts[0] = "C";
            parts[1] = "O";
            parts[2] = "F";
            parts[3] = "F";
            parts[4] = "E";
            parts[5] = "E";

        }else if(n==3){
            parts[0] = "C";
            parts[1] = "A";
            parts[2] = "R";
            parts[3] = "P";
            parts[4] = "E";
            parts[5] = "T";

        }else if(n == 4){
            parts[0] = "W";
            parts[1] = "I";
            parts[2] = "N";
            parts[3] = "T";
            parts[4] = "E";
            parts[5] = "R";

        }else if(n == 5){
            parts[0] = "P";
            parts[1] = "H";
            parts[2] = "O";
            parts[3] = "N";
            parts[4] = "E";
            parts[5] = "S";

        }
    }

    public boolean solved(String[] solution){
        if(solution != null && solution.length == parts.length){
            for(int i = 0; i<parts.length; i++){
                if(!solution[i].equals(parts[i]))
                    return false;
            }
            return true;

        }
        else
            return false;
    }


    public String[] scramble(){
        String [] scramble = new String[parts.length];
        for(int i =0; i<scramble.length; i++){
            scramble[i] = parts[i];
        }
        while(solved(scramble)){
            for(int i =0; i<scramble.length; i++){
                int n = random.nextInt(scramble.length - i) + i;
                String temp = scramble[i];
                scramble[i] = scramble[n];
                scramble[n] = temp;

            }
        }
        return scramble;
    }
    public int getNumOarts(){
        return parts.length;
    }

    //returns the original parts array containg the solution
    public String[] getSolution(){
        return parts;
    }

}
