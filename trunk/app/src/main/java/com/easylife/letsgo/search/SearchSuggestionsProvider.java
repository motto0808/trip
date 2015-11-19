package com.easylife.letsgo.search;

import android.content.SearchRecentSuggestionsProvider;
/**
 * Created by user on 2015/11/19.
 */
public class SearchSuggestionsProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "com.easylife.suggestions";
    public final static int MODE = DATABASE_MODE_QUERIES;
    public SearchSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}