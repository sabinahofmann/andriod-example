package de.hofmanns.traininganalyse;

import java.util.ArrayList;
import java.util.List;

import de.hofmanns.traininganalyse.databse.FeedReaderContract.Training;
import de.hofmanns.traininganalyse.databse.FeedReaderDbHelper;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class DisplayStoreDataActivity extends Activity {
	FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getBaseContext());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// to access database

		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { Training._ID, Training.COLUMN_NAME,
				Training.COLUMN_AMOUNT, Training.COLUMN_CREATED_AT };

		// How you want the results sorted in the resulting Cursor
		String sortOrder = Training.COLUMN_NAME + " DESC";

		Cursor cursor = db.query(Training.TABLE_NAME, // The table to query
				projection, // The columns to return
				null, // selection, // The columns for the WHERE clause
				null, // selectionArgs, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				sortOrder // The sort order
				);

		cursor.moveToFirst();
		long itemId = cursor
				.getLong(cursor.getColumnIndexOrThrow(Training._ID));

		// Set the text view as the activity layout
		//setContentView(listView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_store_data, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_display_store_data, container, false);
			return rootView;
		}
	}
	
	public void onClickItem(){
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(Training._ID, id);
		values.put(Training.COLUMN_NAME, title);
		values.put(Training.COLUMN_CREATED_AT, date);
		values.put(Training.COLUMN_AMOUNT, amount);

		// Insert the new row, returning the primary key value of the new row
		// second argument provides the name of a column in which the framework can insert NULL
		// in the event that the ContentValues is empty 
		// if is set to "null", then the framework will not insert a row when there are no values		
		long newRowId;
		newRowId = db.insert(
		         Training.TABLE_NAME,
		         null,
		         values);
	}

}
