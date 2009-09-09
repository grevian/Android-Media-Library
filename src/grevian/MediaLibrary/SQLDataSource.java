package grevian.MediaLibrary;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLDataSource extends SQLiteOpenHelper {

	private final static String TAG = "GrevianMedia";
	private final static String DATABASE_NAME = "mGrevianDB.db";
	private final static String DATABASE_TABLE = "t_media";
	private final static String DATABASE_SEARCH_TABLE = DATABASE_TABLE + "_SEARCH";
	private final static int DATABASE_VERSION = 2;
	
	private SQLiteDatabase itemDB;
	
	SQLDataSource(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO: Clear the DB every load for a clean DB for now.
		// onUpgrade(this.getWritableDatabase(), 1, 2);
		// testInserts(this.getWritableDatabase());
		itemDB = this.getReadableDatabase();
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + DATABASE_TABLE
						+ "( barcode integer primary key, title varchar, owned int(2), loaned varchar, summary varchar ); ");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_SEARCH_TABLE);
		onCreate(db);
	}

	public SQLiteCursor getTitleSearchCursor() {
		return (SQLiteCursor)itemDB.query(DATABASE_TABLE, new String[] {"title", "barcode", "owned"}, " title like ? ", new String[] {"*"}, null, null, "title asc");
	}
	
	public SQLiteCursor getBarcodeSearchCursor() {
		return (SQLiteCursor)itemDB.query(DATABASE_TABLE, new String[] {"title", "barcode", "owned"}, " barcode = ? ", new String[] {"*"}, null, null, "title asc");
	}

}
