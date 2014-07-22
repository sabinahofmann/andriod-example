package de.hofmanns.traininganalyse.databse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TrainerDataSource {

	// Database fields
	private SQLiteDatabase database;
	private FeedTrainerDbHelper dbHelper;
	private String[] allColumns = { FeedTrainerDbHelper.COLUMN_ID,
			FeedTrainerDbHelper.COLUMN_NAME, FeedTrainerDbHelper.COLUMN_RATES,
			FeedTrainerDbHelper.COLUMN_AMOUNT,
			FeedTrainerDbHelper.COLUMN_CREATED_AT };

	public TrainerDataSource(Context context) {
		dbHelper = new FeedTrainerDbHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	@SuppressLint("SimpleDateFormat")
	public Training createTraining(String practice_type, int rates, int amount) {
		ContentValues values = new ContentValues();
		values.put(FeedTrainerDbHelper.COLUMN_NAME, practice_type);
		values.put(FeedTrainerDbHelper.COLUMN_AMOUNT, amount);
		values.put(FeedTrainerDbHelper.COLUMN_RATES, rates);
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	    values.put(FeedTrainerDbHelper.COLUMN_CREATED_AT, dateFormat.format(new Date()));

		long insertId = database.insert(FeedTrainerDbHelper.TABLE_NAME, null,
				values);
		Cursor cursor = database.query(FeedTrainerDbHelper.TABLE_NAME,
				allColumns, FeedTrainerDbHelper.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Training newTraining = cursorToTraining(cursor);
		cursor.close();
		return newTraining;
	}

	public void deleteTraining(Training training) {
		long id = training.getId();
		// Define 'where' part of query.
		String selection = FeedTrainerDbHelper.COLUMN_NAME + " LIKE ?";
		// Specify arguments in placeholder order.
		String[] selectionArgs = { String.valueOf(id) };
		// Issue SQL statement.
		database.delete(FeedTrainerDbHelper.TABLE_NAME, selection,
				selectionArgs);
		System.out.println("Comment deleted with id: " + id);

	}

	public ArrayList<Training> getAllTrainings() {
		ArrayList<Training> trainings = new ArrayList<Training>();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { FeedTrainerDbHelper.COLUMN_ID,
				FeedTrainerDbHelper.COLUMN_NAME,
				FeedTrainerDbHelper.COLUMN_RATES,
				FeedTrainerDbHelper.COLUMN_AMOUNT,
				FeedTrainerDbHelper.COLUMN_CREATED_AT };

		// How you want the results sorted in the resulting Cursor
		String sortOrder = FeedTrainerDbHelper.COLUMN_NAME + " DESC";

		Cursor cursor = database.query(FeedTrainerDbHelper.TABLE_NAME, // The
																		// table
																		// to
				// query
				projection, // The columns to return
				null, // selection, // The columns for the WHERE clause
				null, // selectionArgs, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				sortOrder // The sort order
				);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Training training = cursorToTraining(cursor);
			Log.d("ON CREATE", "training practice_type and rates ");
			trainings.add(training);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return trainings;
	}

	private Training cursorToTraining(Cursor cursor) {
		Training training = new Training(cursor.getLong(0),
				cursor.getString(1),cursor.getInt(2),cursor.getInt(3),
				cursor.getString(4));
		return training;
	}
	
	public Training getTraining(int id){
		String[] columns = { FeedTrainerDbHelper.COLUMN_ID,
				FeedTrainerDbHelper.COLUMN_NAME,
				FeedTrainerDbHelper.COLUMN_RATES,
				FeedTrainerDbHelper.COLUMN_AMOUNT,
				FeedTrainerDbHelper.COLUMN_CREATED_AT };
		//build query
        Cursor cursor = 
        		database.query(FeedTrainerDbHelper.TABLE_NAME, // a. table
        				columns, // b. column names
        		" _id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build object
        Training training = new Training(cursor.getLong(0),
				cursor.getString(1),cursor.getInt(2),cursor.getInt(3),
				cursor.getString(4));


        //log 
        Log.d("getTraining("+id+")", training.toString());

        // 5. return book
        return training;
	}
}
