package de.hofmanns.traininganalyse.databse;

import de.hofmanns.traininganalyse.databse.FeedReaderContract.Training;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {

	private static final String TEXT_TYPE = " TEXT";
	private static final String INT_TYPE = " INTEGER";
	private static final String DATE_TYPE = " DATE";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ Training.TABLE_NAME + " (" + Training._ID
			+ " INTEGER PRIMARY KEY," + Training.COLUMN_NAME + TEXT_TYPE
			+ COMMA_SEP + Training.COLUMN_AMOUNT + INT_TYPE + COMMA_SEP
			+ Training.COLUMN_CREATED_AT + DATE_TYPE + ")";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ Training.TABLE_NAME;
	
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "trainer.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
