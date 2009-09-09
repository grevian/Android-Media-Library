package grevian.MediaLibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MediaFactory {
	private final static String TAG = "GrevianMedia";
	
	public static Media getMediaByUPC(Context mContext, String UPC) throws LookupException
	{
		// Create our new empty media object
		Media mMedia = new Media();
		
		// Try a lookup in our local database first
		SQLDataSource mDataSource = new SQLDataSource(mContext);
		SQLiteCursor mSearch = mDataSource.getBarcodeSearchCursor();
		String[] mArgs = {UPC};
		mSearch.setSelectionArguments(mArgs);
		mSearch.requery();
		
		if ( mSearch.getCount() > 0 )
		{
			mSearch.moveToFirst();
			Log.i(TAG, "Successful SQL Search for UPC : " + UPC);
			Log.i(TAG, "Got Title: " + mSearch.getString(0) );
			mMedia.setTitle(mSearch.getString(0) );
			mMedia.setUPC(UPC);
			mMedia.setOwned(mSearch.getInt(2));
		}
		else
		{
			// Look it up online, insert it into the SQL database cache if we successfully find it online too.
			Log.w(TAG, "SQL Cache Miss for UPC: " + UPC);
			Log.i(TAG, "Looking up UPC Online...");
			String Title = UPCDataSource.getUPCText(UPC);
			Log.i(TAG, "UPC Lookup Result: " + Title);
			
			if ( Title == "" )
			{
				mSearch.close();
				mDataSource.close();
				throw new LookupException("Could not find Title Anywhere!");
			}
	        	
			mMedia.setOwned(0);
			mMedia.setTitle(Title);
			mMedia.setUPC(UPC);
			
			// FIXME: That's some pretty tight coupling ALL OVER here...
			Log.i(TAG, "Inserting looked up result into Database for future reference...");
			SQLiteDatabase mInsertDB = mDataSource.getWritableDatabase();
			ContentValues mVals = new ContentValues();
			mVals.put("title", mMedia.getTitle());
			mVals.put("barcode", mMedia.getUPC());
			mVals.put("owned", mMedia.getOwned());
			mInsertDB.insertOrThrow("t_media", "", mVals);
			
		}

		mSearch.close();
		mDataSource.close();
		
		return mMedia;
	}
	
}
