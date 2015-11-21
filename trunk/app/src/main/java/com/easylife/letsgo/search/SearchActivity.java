package com.easylife.letsgo.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easylife.letsgo.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_bar);
        setSupportActionBar(toolbar);

        Intent intent  = getIntent();
        String query = intent.getStringExtra(SearchManager.QUERY);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestionsProvider.AUTHORITY, SearchSuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        }
        else if(Intent.ACTION_VIEW.equals(intent.getAction())){
            Toast.makeText(this,"By Suggestion", Toast.LENGTH_SHORT).show();
        }

        TextView textView = (TextView) findViewById(R.id.search_keyword);
        textView.setText(query);
    }

}
