package de.hofmanns.traininganalyse.databse;

import android.provider.BaseColumns;

public final class FeedReaderContract {
	// To prevent someone from accidentally instantiating the contract class,
	// give it an empty constructor.
	public FeedReaderContract() {
	}

	/* Inner class that defines the table contents */
	public static abstract class Training implements BaseColumns {
		public static final String TABLE_NAME = "training";
		public static final String COLUMN_NAME = "practice_type";
		public static final String COLUMN_AMOUNT = "amount";
		public static final String COLUMN_CREATED_AT = "created_at";
	}
}
