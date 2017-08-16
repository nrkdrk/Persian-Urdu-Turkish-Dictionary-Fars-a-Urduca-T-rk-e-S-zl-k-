package com.nrkdrk.dictionary.db;

import android.os.AsyncTask;

import com.nrkdrk.dictionary.adapter.WordListAdapter;
import com.nrkdrk.dictionary.adapter.WordListAdapter1;
import com.nrkdrk.dictionary.model.Word;

import java.util.List;

/**
 * Created by Berk Can on 16.08.2017.
 */

public class DataLoader1 extends AsyncTask<String, Void, List<Word>> {

    private DictionaryDB1 dictionaryDB;
    private WordListAdapter1 adapter;

    public DataLoader1(DictionaryDB1 dictionaryDB, WordListAdapter1 adapter) {
        this.dictionaryDB = dictionaryDB;
        this.adapter = adapter;
    }

    @Override
    protected List<Word> doInBackground(String... params) {
        return dictionaryDB.getWords(params[0]);
    }

    @Override
    protected void onPostExecute(List<Word> result) {
        adapter.updateEntries(result);
    }
}
