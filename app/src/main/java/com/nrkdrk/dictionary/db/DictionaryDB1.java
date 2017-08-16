package com.nrkdrk.dictionary.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.nrkdrk.dictionary.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Berk Can on 16.08.2017.
 */

public class DictionaryDB1 {

    public static final String ID = "id";
    public static final String ENGLISH = "en_word";
    public static final String BANGLA = "bn_word";
    public static final String STATUS = "status";
    public static final String USER = "user_created";
    public static final String TABLE_NAME = "word1";

    public static final String BOOKMARKED = "b";
    public static final String USER_CREATED = "u";
    public static final int durum = 0;

    public static DatabaseInitializer initializer;

    public DictionaryDB1(DatabaseInitializer initializer) {
        this.initializer = initializer;
    }


    public void addWord(String englishWord, String banglaWord) {
        SQLiteDatabase db = initializer.getWritableDatabase();
        String sql;
        sql = "INSERT INTO " + TABLE_NAME + " (" + ENGLISH +
                ", " + BANGLA + ", " + USER + ") VALUES ('" + englishWord +
                "', '" + banglaWord + "', '" + USER_CREATED + "') ";

        db.execSQL(sql);
    }

    public static void delete(String id) {

        SQLiteDatabase db = initializer.getReadableDatabase();
        db.delete(TABLE_NAME, ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public List<Word> getWords(String englishWord) {
        String sql;
        sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + ENGLISH + " LIKE ? ORDER BY " + ENGLISH + " LIMIT 100";

        SQLiteDatabase db = initializer.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, new String[]{englishWord + "%"});

            List<Word> wordList = new ArrayList<Word>();
            while(cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String english = cursor.getString(1);
                String bangla = cursor.getString(2);
                String status = cursor.getString(3);
                wordList.add(new Word(id, english, bangla, status));
            }

            return wordList;
        } catch (SQLiteException exception) {
            exception.printStackTrace();
            return null;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public List<Word> getBookmarkedWords() {
        SQLiteDatabase db = initializer.getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + STATUS + " = '" + BOOKMARKED + "'";

        Cursor cursor = db.rawQuery(sql, null);

        List<Word> wordList = new ArrayList<Word>();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String english = cursor.getString(1);
            String bangla = cursor.getString(2);
            String status = cursor.getString(3);
            wordList.add(new Word(id, english, bangla, status));
        }

        cursor.close();
        db.close();
        return wordList;
    }

    public void bookmark(int _id) {
        SQLiteDatabase db = initializer.getWritableDatabase();

        String sql = "UPDATE " + TABLE_NAME + " SET " + STATUS + " = '"
                + BOOKMARKED + "' WHERE " + ID + " = " + _id;
        db.execSQL(sql);
        db.close();
    }

    public void deleteBookmark(int _id) {
        SQLiteDatabase db = initializer.getWritableDatabase();

        String sql = "UPDATE " + TABLE_NAME + " SET " + STATUS + " = '' " +
                " WHERE " + ID + " = " + _id;
        db.execSQL(sql);
        db.close();
    }

}
