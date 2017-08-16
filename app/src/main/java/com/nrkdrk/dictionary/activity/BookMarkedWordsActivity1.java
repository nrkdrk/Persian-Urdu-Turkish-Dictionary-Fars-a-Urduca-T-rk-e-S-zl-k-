package com.nrkdrk.dictionary.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.nrkdrk.dictionary.R;
import com.nrkdrk.dictionary.adapter.WordListAdapter;
import com.nrkdrk.dictionary.adapter.WordListAdapter1;
import com.nrkdrk.dictionary.db.DatabaseInitializer;
import com.nrkdrk.dictionary.db.DictionaryDB1;
import com.nrkdrk.dictionary.model.Word;

import java.util.List;

/**
 * Created by Berk Can on 16.08.2017.
 */

public class BookMarkedWordsActivity1 extends ListActivity {

    private DictionaryDB1 dictionaryDB;
    private WordListAdapter1 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarked);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseInitializer initializer = new DatabaseInitializer(getBaseContext());
        dictionaryDB = new DictionaryDB1(initializer);

        adapter = new WordListAdapter1(this, dictionaryDB);
        setListAdapter(adapter);

        List<Word> bookmarkedWords = dictionaryDB.getBookmarkedWords();
        adapter.updateEntries(bookmarkedWords);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}