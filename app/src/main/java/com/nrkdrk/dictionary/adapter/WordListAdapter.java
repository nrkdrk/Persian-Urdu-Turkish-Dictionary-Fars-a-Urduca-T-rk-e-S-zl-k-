package com.nrkdrk.dictionary.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.nrkdrk.dictionary.R;
import com.nrkdrk.dictionary.activity.FarscaActivity;
import com.nrkdrk.dictionary.db.DictionaryDB;
import com.nrkdrk.dictionary.model.Word;

public class WordListAdapter extends BaseAdapter {

	private List<Word> wordList;
	private Activity context;
	private LayoutInflater mLayoutInflater;
	private DictionaryDB dictionaryDB;
	
	public WordListAdapter(Activity context, DictionaryDB dictionaryDB) {
		this.context = context;
		this.dictionaryDB = dictionaryDB;
		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		wordList = new ArrayList<Word>();
	}

	public int getCount() {
		return wordList.size();
	}

	public Object getItem(int position) {
		return wordList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final Word word = wordList.get(position);
		View view = convertView;
		if (view == null) {
			view = mLayoutInflater.inflate(R.layout.word, null);
		}
		TextView english = (TextView) view.findViewById(R.id.english);
		TextView bangla = (TextView) view.findViewById(R.id.bangla);
		final ImageButton bookmark = (ImageButton) view.findViewById(R.id.bookmark);
		final ImageButton delete = (ImageButton) view.findViewById(R.id.delete);
		delete.setImageResource(R.drawable.delete);
		bangla.setTypeface(Typeface.createFromAsset(context.getAssets(), FarscaActivity.FONT));
		
		english.setText(word.english);
		bangla.setText(word.bangla);
		
		if(word.status != null && word.status.equals(DictionaryDB.BOOKMARKED)) {
			bookmark.setImageResource(R.drawable.bookmarked);
		}
		else {
			bookmark.setImageResource(R.drawable.not_bookmarked);

		}
		
		bookmark.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				bookMarkWord(word, bookmark);
			}
		});

		delete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String id=String.valueOf(wordList.get(position).id);
				String kelime=String.valueOf(wordList.get(position).english);
				Toast.makeText(context, kelime+" silindi", Toast.LENGTH_SHORT).show();
				DictionaryDB.delete(id);
				FarscaActivity.adapter.notifyDataSetChanged();
			}
		});

		return view;
	}

	private void bookMarkWord(final Word word, final ImageButton bookmark) {
		if (word.status != null && word.status.equals(DictionaryDB.BOOKMARKED)) {
			dictionaryDB.deleteBookmark(word.id);
			word.status = "";
			bookmark.setImageResource(R.drawable.not_bookmarked);
			Toast.makeText(context, "Yer İşareti Siliniyor", Toast.LENGTH_SHORT).show();
		}
		else {
			dictionaryDB.bookmark(word.id);
			word.status = DictionaryDB.BOOKMARKED;
			bookmark.setImageResource(R.drawable.bookmarked);
			Toast.makeText(context, "İşareti Eklendi", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void updateEntries(List<Word> wordList) {
		if (wordList == null) {
			AlertDialog dialog = new AlertDialog.Builder(context)
				.setTitle("Afedersiniz!")
				.setMessage("\n" +"Telefonunuz önceden oluşturulmuş veritabanını desteklemez\nLütfen uygulama verilerini temizleyiniz")
				.setNeutralButton("Exit", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						context.finish();
					}
				})
				.create();
			dialog.show();
		} else {
			this.wordList = wordList;
			notifyDataSetChanged();
		}
	}
}
