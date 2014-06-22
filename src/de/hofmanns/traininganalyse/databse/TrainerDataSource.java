package de.hofmanns.traininganalyse.databse;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

	public Training createTraining(String practice_type, int rates, int amount,
			String created_at) {
		ContentValues values = new ContentValues();
		values.put(FeedTrainerDbHelper.COLUMN_NAME, practice_type);
		values.put(FeedTrainerDbHelper.COLUMN_AMOUNT, amount);
		values.put(FeedTrainerDbHelper.COLUMN_RATES, rates);
		values.put(FeedTrainerDbHelper.COLUMN_CREATED_AT, created_at);

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

	public List<Training> getAllTrainings() {
		List<Training> trainings = new ArrayList<Training>();

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
			trainings.add(training);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return trainings;
	}

	private Training cursorToTraining(Cursor cursor) {
		Training training = new Training();
		training.setId(cursor.getLong(0));
		training.setPracticeType(cursor.getString(1));
		training.setRates(cursor.getInt(2));
		training.setAmount(cursor.getInt(3));
		return training;
	}
}
