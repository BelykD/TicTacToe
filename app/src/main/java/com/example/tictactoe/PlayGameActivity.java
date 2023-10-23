package com.example.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class PlayGameActivity extends AppCompatActivity {

    private ArrayList<String> playerOne = new ArrayList<>(); // ArrayList to keep track of player 1's moves
    private ArrayList<String> playerAndroid = new ArrayList<>(); // ArrayList to keep track of Android's moves
    private ArrayList<String> usedTiles = new ArrayList<>(); // ArrayList to keep track of used tiles

    private boolean playerTurn; // True to start as player 1's turn
    private boolean gameOver; // Bool to check if game is over - stops the ability to click tiles
    private TextView playerTurnText, topText, playingAs;
    private ImageView tileOne, tileTwo, tileThree, tileFour, tileFive, tileSix, tileSeven, tileEight, tileNine;
    private Button reset;
    private ImageView[] allTiles;
    private String player;
    private TicTacToeTask ticTacToeTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game_activity);

        playerTurn = true;
        gameOver = false;
        ticTacToeTask = new TicTacToeTask();
        ticTacToeTask.execute(); // Starting AsyncTask

        SharedPreferences sharedPreferences = getSharedPreferences("current_player", Context.MODE_PRIVATE);
        player = sharedPreferences.getString("player", null);

        // Reset button
        reset = findViewById(R.id.reset);
        // Top text
        topText = findViewById(R.id.winner);
        // PLaying as text
        playingAs = findViewById(R.id.playingAs);
        // Player turn text
        playerTurnText = findViewById(R.id.playerTurn);
        // ImageView tile declaration
        tileOne = findViewById(R.id.tile1);
        tileTwo = findViewById(R.id.tile2);
        tileThree = findViewById(R.id.tile3);
        tileFour = findViewById(R.id.tile4);
        tileFive = findViewById(R.id.tile5);
        tileSix = findViewById(R.id.tile6);
        tileSeven = findViewById(R.id.tile7);
        tileEight =  findViewById(R.id.tile8);
        tileNine = findViewById(R.id.tile9);
        // Array of all tiles
        allTiles = new ImageView[] { tileOne, tileTwo, tileThree, tileFour, tileFive, tileSix, tileSeven, tileEight, tileNine };

        playerTurnText.setText("Turn: " + player); // Set initial turn text
        // Setting playing as text
        playingAs.setText("Playing as: " + player);
        // Reset button onClickListener
        reset.setOnClickListener(view -> {
            // Clear all arrays
            playerOne.clear();
            playerAndroid.clear();
            usedTiles.clear();
            // Clear tile images
            for (ImageView image : allTiles) {
                Drawable clearTile = getResources().getDrawable(R.drawable.outline);
                image.setImageDrawable(clearTile);
            }
            playerTurn = true; // Reset player turn
            playerTurnText.setText("Turn: " + player); // Reset turn text
            topText.setText("Tic-Tac-Toe"); // Reset top text
            gameOver = false; // Reset GameOver
            playingAs.setText("Playing as: " + player); // Setting playing as text

        });
        // Tile onClickListeners for all 9 tiles
        tileOne.setOnClickListener(view -> {
            if (!gameOver && playerTurn && !usedTiles.contains("one")) {
                addTile("one"); // Adding tile one to player's array
                setImage(tileOne);
                System.out.println("Tile 1 Clicked");
            }
        });
        tileTwo.setOnClickListener(view -> {
            if (!gameOver && playerTurn && !usedTiles.contains("two")) {
                addTile("two"); // Adding tile two to player's array
                setImage(tileTwo);
                System.out.println("Tile 2 Clicked");
            }
        });
        tileThree.setOnClickListener(view -> {
            if (!gameOver && playerTurn && !usedTiles.contains("three")) {
                addTile("three"); // Adding tile three to player's array
                setImage(tileThree);
                System.out.println("Tile 3 Clicked");
            }
        });
        tileFour.setOnClickListener(view -> {
            if (!gameOver && playerTurn && !usedTiles.contains("four")) {
                addTile("four"); // Adding tile four to player's array
                setImage(tileFour);
                System.out.println("Tile 4 Clicked");
            }
        });
        tileFive.setOnClickListener(view -> {
            if (!gameOver && playerTurn && !usedTiles.contains("five")) {
                addTile("five"); // Adding tile five to player's array
                setImage(tileFive);
                System.out.println("Tile 5 Clicked");
            }
        });
        tileSix.setOnClickListener(view -> {
            if (!gameOver && playerTurn && !usedTiles.contains("six")) {
                addTile("six"); // Adding tile six to player's array
                setImage(tileSix);
                System.out.println("Tile 6 Clicked");
            }
        });
        tileSeven.setOnClickListener(view -> {
            if (!gameOver && playerTurn && !usedTiles.contains("seven")) {
                addTile("seven"); // Adding tile seven to player's array
                setImage(tileSeven);
                System.out.println("Tile 7 Clicked");
            }
        });
        tileEight.setOnClickListener(view -> {
            if (!gameOver && playerTurn && !usedTiles.contains("eight")) {
                addTile("eight"); // Adding tile eight to player's array
                setImage(tileEight);
                System.out.println("Tile 8 Clicked");
            }
        });
        tileNine.setOnClickListener(view -> {
            if (!gameOver && playerTurn && !usedTiles.contains("nine")) {
                addTile("nine"); // Adding tile nine to player's array
                setImage(tileNine);
                System.out.println("Tile 9 Clicked");
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        ticTacToeTask = new TicTacToeTask();
        ticTacToeTask.execute(); // Starting AsyncTask
        Log.d("MyActivity", "onRestart called");
    }
    // Method to determine player turn and set tile image
    public void setImage(ImageView tile) {
        Drawable changeTile = getResources().getDrawable(R.drawable.pumpkin);
        tile.setImageDrawable(changeTile);
    }
    // Method to add tile to player's array and check for winner
    public void addTile(String tile) {
        usedTiles.add(tile); // Add tile to usedTiles to avoid duplicate picks
        if (playerTurn) {
            playerOne.add(tile); // Add to player 1's tiles
            playerTurn = false; // Set to player 2's turn
            playerTurnText.setText("Turn: Android");
            isWinner(playerOne, player); // Check if this tile wins player 1 the game
        }
    }
    // Method to set player score
    public void setScore(String player, int score) {
        try {
            String fileName = player + "_score.txt"; // File name with the player's name
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            String scoreData = String.valueOf(score);
            fos.write(scoreData.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Method to get player score
    public int getScore(String player) {
        int score = 0;
        String fileName = player + "_score.txt";
        if (checkIfFileExists(fileName)) {
            FileInputStream fis = null;
            try {
                fis = openFileInput(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String scoreData = reader.readLine();
                score = Integer.parseInt(scoreData);
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return score;
    }
    // Method to check if file exists before trying to get data from it
    public boolean checkIfFileExists(String fileName) {
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }
    // Method to handle wins
    private void isWinner(ArrayList<String> player, String currentPlayer) {
        String[] winningCombos = {
                // Horizontal rows
                "one two three", "four five six", "seven eight nine",
                // Vertical rows
                "one four seven", "two five eight", "three six nine",
                // Diagonals
                "one five nine", "three five seven"
        };
        // Split combos then check if the player's array contains all the tiles from a combo
        for (String combos : winningCombos) {
            String[] moves = combos.split(" ");
            boolean winner = true;
            // Checking for winning combos in player's array
            for (String move : moves) {
                if (!player.contains(move)) {
                    winner = false;
                    break;
                }
            }
            if (winner) {
                //System.out.println("Winner!");
                topText.setText(currentPlayer + " wins!"); // Set top text to display "Winner!"
                gameOver = true; // Set gameOver bool to true to disable clicking more tiles
                playerTurnText.setText("Game Over!"); // Display "Game Over!" instead of current turn
                playingAs.setText(""); // Hide "Playing as" text
                if (currentPlayer != "Dylan") { // Set score for player
                    int newScore = getScore(String.valueOf(currentPlayer)) + 1; // Get current score and add 1
                    Log.d("Debug", "Player Name: " + currentPlayer + " Score: " + newScore);
                    setScore(currentPlayer, newScore); // Set new score
                    System.out.println("Player New Score: " + getScore(String.valueOf(player)));
                } else { // Set score for Android
                    int newScore = getScore("Android") + 1; // Get current score and add 1
                    setScore("Android", newScore); // Set new score
                    System.out.println("Android New Score: " + getScore("Android"));
                }
            }
        }
    }
    // ASyncTask
    private class TicTacToeTask extends AsyncTask<Integer, String, Integer> {
        private final Handler handler = new Handler();
        private final long delay = 1000;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            handler.postDelayed(gameRunnable, delay);
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            String[] tiles = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
            int random;
            // Background operations
            while (true) {
                try {
                    Thread.sleep(1500); // Delay Android's move
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!playerTurn) {
                    // Android's turn code
                    Random rand = new Random();
                    do { // Generating random number to be used as Android's next tile pick
                        random = rand.nextInt(8) + 1;
                    } while (usedTiles.contains(tiles[random]));
                    // Publishes string of number and number - "five, 5"
                    publishProgress(tiles[random], String.valueOf(random));
                    System.out.println("New Move Android: " + random);
                }
            }
        }
        @Override
        protected void onProgressUpdate(String... strings) {
            super.onProgressUpdate(strings);
            System.out.println("Progress update: " + strings[0] + " Random: " + strings[1]);
            if (!playerTurn && !gameOver) {
                addTile(String.valueOf(strings[0])); // Adds Androids tile to used tiles
                playerAndroid.add(String.valueOf(strings[0])); // Add to player 1's tiles
                usedTiles.add(strings[0]);
                playerTurnText.setText("Turn: " + player);
                Drawable changeTile = getResources().getDrawable(R.drawable.ghost);
                allTiles[Integer.parseInt(strings[1])].setImageDrawable(changeTile);
            }
            isWinner(playerAndroid, "Android"); // Check if Android has won the game
            playerTurn = true; // Set turn back to player
            System.out.println(usedTiles);
        }
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }

        private Runnable gameRunnable = new Runnable() {
            @Override
            public void run() {
                if (!gameOver) {
                    System.out.println("Runnable running!");
                    handler.postDelayed(this, delay);
                }
            }
        };
    }
}
