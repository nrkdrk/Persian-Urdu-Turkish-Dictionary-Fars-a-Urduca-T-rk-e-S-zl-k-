package com.nrkdrk.dictionary.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.nrkdrk.dictionary.R;
import com.nrkdrk.dictionary.adapter.WordListAdapter;
import com.nrkdrk.dictionary.db.DataLoader;
import com.nrkdrk.dictionary.db.DatabaseInitializer;
import com.nrkdrk.dictionary.db.DictionaryDB;

public class FarscaActivity extends ListActivity {
	private EditText input;
	private TextView empty;
	
	private DictionaryDB dictionaryDB;
	public static WordListAdapter adapter;
	DatabaseInitializer initializer;

	public static final String FONT = "SolaimanLipi.ttf";
	public static int durum=0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		input = (EditText) findViewById(R.id.input);
		empty = (TextView) findViewById(android.R.id.empty);

		initializer = new DatabaseInitializer(getBaseContext());
		initializer.initializeDataBase();
		dictionaryDB = new DictionaryDB(initializer);

		adapter = new WordListAdapter(this, dictionaryDB);
		setListAdapter(adapter);

        input.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				loadData(input.getText().toString());
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			public void afterTextChanged(Editable s) {
				
			}
		});
    }
    
    private void loadData(String word) {
		DataLoader loader = new DataLoader(dictionaryDB, adapter);
		loader.execute(word);
		if(word.equals("")){

		} else
			empty.setText("Eşleşme bulunamadı");
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	loadData(input.getText().toString());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getItemId() == R.id.bookmarked_words) {
    		Intent intent = new Intent(this, BookMarkedWordsActivity.class);
    		startActivity(intent);
    	}else if(item.getItemId() == R.id.changeDic) {
    		if (durum==0){
				Intent ıntent=new Intent(FarscaActivity.this,UrducaActivity.class);
				startActivity(ıntent);
				finish();
				durum=1;
			}else if (durum==1){
				Intent ıntent=new Intent(FarscaActivity.this,FarscaActivity.class);
				startActivity(ıntent);
				finish();
				durum=0;
			}
    	}else if(item.getItemId() == R.id.about) {
    		Intent intent = new Intent(this, AboutActivity.class);
    		startActivity(intent);
    	}else if(item.getItemId() == R.id.add_new) {
    		showInputDialog();
    	}
    	
    	return super.onOptionsItemSelected(item);	
    }
    
	public void showInputDialog() {
		LayoutInflater factory = LayoutInflater.from(this);

		final View addNew = factory.inflate(R.layout.add_new, null);

		final EditText farsca = (EditText) addNew.findViewById(R.id.farsca_input);
		final EditText turkish = (EditText) addNew.findViewById(R.id.turkish_input);

		final TextView dialog_label = (TextView) addNew.findViewById(R.id.dialog_label);
		dialog_label.setText("Farsça:");

		turkish.setTypeface(Typeface.createFromAsset(getAssets(), FarscaActivity.FONT));

		final AlertDialog.Builder newWordInputDialog = new AlertDialog.Builder(this);
		newWordInputDialog
			.setTitle("\n" +
					"Yeni kelime ekle(Farsça-Türkçe)")
			.setView(addNew)
			.setPositiveButton("Kaydet",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							addNewWord(farsca, turkish);
							adapter.notifyDataSetChanged();
						}
					})
			.setNegativeButton("Vazgeç",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {}
					});
		newWordInputDialog.show();
	}

	private void addNewWord(final EditText english, final EditText bangla) {
		String englishWord = english.getText().toString();
		String banglaWord = bangla.getText().toString();
		if((englishWord.equals("") || banglaWord.equals("")))
			Toast.makeText(this, "\n" +
							"Alan boş bırakılamaz",
					Toast.LENGTH_SHORT).show();
		else {
			dictionaryDB.addWord(englishWord, banglaWord);
			
			Toast.makeText(this, "Sözlüğe kelime eklendi",
					Toast.LENGTH_SHORT).show();
		}
	}

}