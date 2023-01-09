package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText guessEditText;
    TextView resultTextView;
    Button checkButton;
    int min = 1;
    int max = 25;
    int randomNumber = getRandomNumber(min, max);
    boolean gameWon = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guessEditText = findViewById(R.id.guessText);
        resultTextView = findViewById(R.id.resultTextView);
        checkButton = findViewById(R.id.checkButton);
        System.out.println("Chosen number is " + randomNumber);
    }

    public void onClickCheck(View view) {
        int number = Integer.parseInt(guessEditText.getText().toString());
        if (number < randomNumber) {
            makeToast("Not correct, you have to think of a bigger number");
        } else if (number > randomNumber) {
            makeToast("Not correct, you have to think of a smaller number");
        } else {
            makeToast("Correct, you guessed!");
            resultTextView.setText("You won.\nThe number was " + randomNumber);
            gameWon = true;
        }
    }


    public void onClickStartThread(View view) {
        makeToast("Thread started, try guessing faster!");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!gameWon) {
                    int threadNumber = getRandomNumber(min, max);
                    System.out.println("Thread guessed: " + threadNumber);

                    if (threadNumber == randomNumber) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultTextView.setText("You lost.\n The number was " + randomNumber);
                            }
                        });
                        gameWon = true;
                    } else {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        if(gameWon) {
            guessEditText.setFocusable(false);
            checkButton.setFocusable(false);
        }
    }

    public void onClickNewGame(View view) {
        gameWon = false;
        resultTextView.setText("Game was restarted. No one guessed yet");
        randomNumber = getRandomNumber(min, max);
        guessEditText.setFocusableInTouchMode(true);
        checkButton.setFocusableInTouchMode(true);
        guessEditText.setText("");
        System.out.println("The new chosen number is: " + randomNumber);
    }

    static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void makeToast(String string) {
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
    }
}