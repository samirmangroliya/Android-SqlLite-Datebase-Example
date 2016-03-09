package com.samir.spinner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {

	private EditText edittext;
	private Button btnAdd;
	private Spinner spinner;
	DatabaseHelper db;
	List<String> list;
	ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		db = new DatabaseHelper(MainActivity.this);

		edittext = (EditText) findViewById(R.id.editText1);
		btnAdd = (Button) findViewById(R.id.btnadd);
		spinner = (Spinner) findViewById(R.id.spinner1);

		btnAdd.setOnClickListener(this);
		spinner.setOnItemSelectedListener(this);
	}

	@Override
	public void onClick(View v) {

		String name = edittext.getText().toString().trim();

		if (TextUtils.isEmpty(name)) {
			 showToast("Please Enter Your Name");

			edittext.requestFocus();
		} else {
			db.insertData(name);
			edittext.setText("");

			// Hiding the keyboard
			InputMethodManager inputmangager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmangager.hideSoftInputFromWindow(edittext.getWindowToken(), 0);

			loadSpinner();
		}

	}

	private void loadSpinner() {
		// here i used Set Because Set doesn't allow duplicates.
		Set<String> set = db.getAllData();

		List<String> list = new ArrayList<String>(set);

		adapter = new ArrayAdapter<String>(MainActivity.this,
				android.R.layout.simple_spinner_item, list);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(adapter);
		spinner.setWillNotDraw(false);

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		String name = parent.getItemAtPosition(position).toString();
		showToast("Selected Item :: " + name);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

}
