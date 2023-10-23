package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    String[] menuItems = {"Enter Names", "Play Game", "Standings", "How to Play"}; // Array of menu items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // Getting ListView
        final ListView listView = (ListView)findViewById(R.id.item_list);
        // Clear ListView focus for visual purposes
        listView.clearFocus();
        // Setting adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainMenu.this, android.R.layout.simple_list_item_1, menuItems);
        listView.setAdapter(adapter);
        // OnClickListener for ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String selectedItem = menuItems[pos];
                if (selectedItem.equals("Enter Names")) {
                    openNames(); // Opens names activity
                } else if (selectedItem.equals("Play Game")) {
                    openGame(); // Opens game activity
                } else if (selectedItem.equals("Standings")) {
                    openStandings(); // Opens standings activity
                } else if (selectedItem.equals("How to Play")) {
                    openInstructions(); // Opens instructions page
                }
            }
        });
    }

    // Methods to open new windows using intents
    public void openGame() { // Opens PlayGameActivity
        Intent intent = new Intent(MainMenu.this, PlayGameActivity.class);
        startActivity(intent);
    }
    public void openNames() { // Opens EnterNamesActivity
        Intent intent = new Intent(MainMenu.this, EnterNamesActivity.class);
        startActivity(intent);
    }
    public void openStandings() { // Opens ShowStandingsActivity
        Intent intent = new Intent(MainMenu.this, ShowStandingsActivity.class);
        startActivity(intent);
    }
    public void openInstructions() {
        Intent intent = new Intent(MainMenu.this, InstructionsActivity.class);
        startActivity(intent);
    }
}
