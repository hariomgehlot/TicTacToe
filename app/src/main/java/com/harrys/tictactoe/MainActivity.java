package com.harrys.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button[][] buttons = new Button[3][3];
    TextView playerredpoint ;
    TextView playerbluepoint ;

    int playerRedPoint = 0;
    int playerBluePoint = 0;
    boolean playerRedTurn = true;
    Button reset ;
    int roundCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerredpoint = findViewById(R.id.text_view_p1);
        playerbluepoint = findViewById(R.id.text_view_p2);
        reset = findViewById(R.id.button_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });
        reset.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                resetGame();
                return true;
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonId = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        resetColor();
    }
        @Override
        public void onClick (View v){
            if (!((Button) v).getText().toString().equals("")) {
                return;
            }

            if (playerRedTurn){
                ((Button)v).setText("X");
                ((Button)v).setBackgroundColor(Color.RED);
            }else{
                ((Button)v).setText("O");
                ((Button)v).setBackgroundColor(Color.BLUE);
            }
            roundCount++;

            if (checkForWin()) {
                if (playerRedTurn) {
                    player1Wins();
                } else {
                    player2Wins();
                }
            } else if (roundCount == 9) {
                draw();
            } else {
                playerRedTurn = !playerRedTurn;
            }

        }
    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        return field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("");
    }



    private void player1Wins() {
        playerRedPoint++;
        Toast.makeText(this, "Player Red Won!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
        resetColor();
    }
    private void player2Wins() {
        playerBluePoint++;
        Toast.makeText(this, "Player Blue Won!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
        resetColor();
    }
    private void draw() {
        Toast.makeText(this, "TIEEE!", Toast.LENGTH_SHORT).show();
        resetBoard();
        resetColor();
    }
    private void updatePointsText() {
       playerredpoint.setText("Player Red: " + playerRedPoint);
        playerbluepoint.setText("Player Blue: " + playerBluePoint);
    }
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        playerRedTurn = true;
    }
    private void resetColor(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setBackgroundColor(Color.WHITE);
            }
        }
    }

    private void resetGame() {
        playerRedPoint = 0;
        playerBluePoint = 0;
        updatePointsText();
        resetBoard();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("PlayerRedPoints", playerRedPoint);
        outState.putInt("player2Points", playerBluePoint);
        outState.putBoolean("PlayerRedTurn", playerRedTurn);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        playerRedPoint = savedInstanceState.getInt("PlayerRedPointss");
        playerBluePoint = savedInstanceState.getInt("player2Points");
        playerRedTurn = savedInstanceState.getBoolean("PlayerRedTurn");
    }
}


    