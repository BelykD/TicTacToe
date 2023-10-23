package com.example.tictactoe;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ShowStandingsActivity extends AppCompatActivity {

    String FILENAME = "game_data.txt";
    ListView standingsList;
    private ArrayList<String> playerList = new ArrayList<>();
    private ArrayList<String> playerNames = new ArrayList<>();
    private Button clear;
    private ArrayAdapter<String> playerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_standings_activity);

        restoreUserName();
        System.out.println(playerList);

        clear = findViewById(R.id.clearStandings);
        clear.setOnClickListener(view -> {
            clearScores();
        });

        standingsList = findViewById(R.id.standingsList);
        // Adapter to add player names to ListView
        playerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playerList);
        standingsList.setAdapter(playerAdapter);
    }
    public void clearScores() {
        // Clear all player scores
        for (String player : playerNames) {
            setScore(player, 0);
        }
        // Clear Android's score
        setScore("Android", 0);
        // Update adapter
        playerList.clear();
        playerList.add("Android: 0");
        readFile();
        playerAdapter.notifyDataSetChanged();
    }
    public void restoreUserName() {
        // Add Android player to ArrayList to add to ListView
        playerList.add("Android: " + getScore("Android"));
        readFile();
    }
    // Method to read local file
    public void readFile() {
        if (checkIfFileExists(FILENAME)) {
            // Read contents of file and update
            FileInputStream fis = null;
            try {
                fis = openFileInput(FILENAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String sLine;
            try {
                while ((sLine = br.readLine()) != null) {
                    int score = getScore(sLine);
                    playerList.add(sLine + ": " + score); // Add player names to list
                    playerNames.add(sLine); // Add player names to list to be used for clearing scores
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("File read!");
        } else {
            System.out.println("File doesn't exist!");
        }
    }

    public boolean checkIfFileExists(String fileName) {
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
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
}
