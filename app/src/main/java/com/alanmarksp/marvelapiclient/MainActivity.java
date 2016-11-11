package com.alanmarksp.marvelapiclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.alanmarksp.marvelapiclient.adapters.CharacterAdapter;
import com.alanmarksp.marvelapiclient.loaders.CharacterLoader;
import com.alanmarksp.marvelapiclient.models.Character;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String PAGE_KEY = "page";
    public static final int PAGE_SIZE = 10;
    private int currentPage = 0;

    private List<Character> characters;
    private CharacterAdapter characterAdapter;

    private CharacterLoader characterLoader;

    private ProgressBar progressBar;
    private Button previousButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        characterLoader = new CharacterLoader(this);

        characters = new ArrayList<>();

        characterAdapter = new CharacterAdapter(this, R.layout.character_row, characters);

        ListView charactersListView = (ListView) findViewById(R.id.characters_list_view);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        charactersListView.setAdapter(characterAdapter);

        previousButton = (Button) findViewById(R.id.previous_button);
        nextButton = (Button) findViewById(R.id.next_button);

        if (savedInstanceState != null) {
            currentPage = savedInstanceState.getInt(PAGE_KEY);
        }

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPreviousPage();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNextPage();
            }
        });

        new LoadCharactersTask().execute(currentPage);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(PAGE_KEY, currentPage);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        currentPage = savedInstanceState.getInt(PAGE_KEY);
    }

    private class LoadCharactersTask extends AsyncTask<Integer, Void, List<Character>> {

        @Override
        protected void onPreExecute() {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Character> doInBackground(Integer... ints) {
            try {
                return characterLoader.loadPage(ints[0]);
            } catch (IOException e) {
                Log.d("Debug", "doInBackground: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Character> characters) {
            loadCharacters(characters);
            progressBar.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private void loadNextPage() {
        currentPage++;
        new LoadCharactersTask().execute(currentPage);
    }

    private void loadPreviousPage() {
        currentPage--;
        new LoadCharactersTask().execute(currentPage);
    }

    private void loadCharacters(List<Character> characters) {
        if (characters == null) {
            return;
        }

        this.characters.clear();
        this.characters.addAll(characters);
        characterAdapter.notifyDataSetChanged();

        if (characters.size() < PAGE_SIZE) {
            nextButton.setEnabled(false);
        }
        else {
            nextButton.setEnabled(true);
        }

        if (currentPage == 0) {
            previousButton.setEnabled(false);
        }
        else {
            previousButton.setEnabled(true);
        }
    }
}
