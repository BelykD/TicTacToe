package com.example.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EnterNamesActivity extends AppCompatActivity {

    String FILENAME = "game_data.txt";
    EditText username;
    Button saveName, clearNames;
    ListView players;
    TextView currentPlayer;

    private ArrayList<String> playerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_names_activity);
        // Current player SharedPreferences for storing who is currently playing
        SharedPreferences sharedPreferences = getSharedPreferences("current_player", Context.MODE_PRIVATE);
        String player = sharedPreferences.getString("player", null);

        username = (EditText)findViewById(R.id.playerInput);
        saveName = (Button)findViewById(R.id.saveName);
        clearNames = (Button)findViewById(R.id.clearName);
        players = (ListView)findViewById(R.id.playerList);
        currentPlayer = (TextView)findViewById(R.id.currentPlayer);

        // No change if SharedPreferences is empty
        if (player != null) {
            currentPlayer.setText("Playing as: " + player);
        }

        restoreUserName(); // Restore names for players from local file

        // Adapter to add player names to ListView
        ArrayAdapter<String> playerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playerList);
        players.setAdapter(playerAdapter);

        saveName.setOnClickListener(view -> {
            saveUserName(view);
            // Updating ListView when name is saved
            playerList.clear();
            restoreUserName();
            playerAdapter.notifyDataSetChanged(); // Update ListView
            // Clear EditText
            username.setText("");
        });
        clearNames.setOnClickListener(view -> {
            clearFile();
            // Clear SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();  // Clear all data
            editor.apply();  // Save the changes
            // Clear "Playing as" text
            currentPlayer.setText("Playing as: ");
            // Update ListView
            playerList.clear();
            playerList.add("Android");
            playerAdapter.notifyDataSetChanged();
        });
        // OnClickListener to handle picking who the user is playing as
        players.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (playerList.get(pos) != "Android") { // Does not allow user to play as Android
                    setCurrentPlayer(String.valueOf(playerList.get(pos)));
                    currentPlayer.setText("Playing as: " + getCurrentPlayer()); // Changing "Player as" text to the player clicked
                    System.out.println(getCurrentPlayer());
                }
            }
        });
    }
    // Methods to access read and write file methods
    public void saveUserName(View v) {
        writeFile();
    }
    public void restoreUserName() {
        // Add Android player to ArrayList to add to ListView
        playerList.add("Android");
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
            String sLine = null;
            try {
                while ((sLine = br.readLine()) != null) {
                    playerList.add(sLine); // Add player names to list
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
    // Method to write to local file
    private void writeFile() {
        // Make sure edit text has text in it then write file
        if (!username.getText().toString().trim().isEmpty()) {
            String content = username.getText().toString();
            try {
                FileOutputStream fos = openFileOutput(FILENAME, MODE_APPEND);
                String contentNewLine = content + "\n";
                fos.write(contentNewLine.getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("File written!");
        }
    }
    // Method to clear local file of names
    private void clearFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
            fos.close();
            System.out.println("File cleared!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Method to set current player
    public void setCurrentPlayer(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("current_player", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("player", name);
        editor.apply();
    }
    // Method to get current player
    public String getCurrentPlayer() {
        SharedPreferences sharedPreferences = getSharedPreferences("current_player", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("player", null);

        return name;
    }
}

