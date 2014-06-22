package de.hofmanns.traininganalyse.databse;

//import de.hofmanns.traininganalyse.databse.FeedTrainerContract.Training;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedTrainerDbHelper extends SQLiteOpenHelper {
	
	// define the database
	public static final String COLUMN_ID = "_id";
	public static final String TABLE_NAME = "trainings";
	public static final String COLUMN_NAME = "practice_type";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_RATES = "rates";
	public static final String COLUMN_CREATED_AT = "created_at";
	private static final String TEXT_TYPE = " TEXT";
	private static final String INT_TYPE = " INTEGER";
	private static final String DATE_TYPE = " DATE";
	private static final String COMMA_SEP = ",";
	
	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "trainesr.db";
	
	 // Database creation sql statement
	private static final String DATABASE_CREATE  = "CREATE TABLE "
			+ TABLE_NAME + " (" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + TEXT_TYPE
			+ COMMA_SEP + COLUMN_AMOUNT + INT_TYPE 
			+ COMMA_SEP + COLUMN_RATES + INT_TYPE
			+ COMMA_SEP + COLUMN_CREATED_AT + DATE_TYPE + ")";

	private static final String DELETE_DATABASE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;
	

    public FeedTrainerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DELETE_DATABASE);
        onCreate(db);
    }
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        onUpgrade(db, oldVersion, newVersion);
//    }
}
