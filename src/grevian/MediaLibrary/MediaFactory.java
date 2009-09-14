package grevian.MediaLibrary;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class MediaFactory {
	private final static String TAG = "GrevianMedia";
	
	public static Media getMediaByUPC(Context mContext, String UPC) throws LookupException
	{
		Media mMedia = null;
		ContentResolver cr = mContext.getContentResolver();
		
		// Try to lookup the content with the CR first
		Uri myItem = ContentUris.withAppendedId(Media.CONTENT_URI, Long.valueOf(UPC));
		Cursor cur = cr.query(myItem, null, null, null, null);
		if ( cur.getCount() > 0 )
		{
			cur.moveToFirst();
			mMedia = new Media(cur, cr);
		}
		cur.close();
		
		if ( mMedia != null )
			return mMedia;
		
		// Look it up online, then insert it into the CR if we successfully find it 
		Log.w(TAG, "ContentResolver Miss for UPC: " + UPC);
		Log.i(TAG, "Looking up UPC Online...");
		String Title = UPCDataSource.getUPCText(UPC).trim();			
		Log.i(TAG, "UPC Lookup Result: " + Title);
		
		if ( Title == "" )
			throw new LookupException("Could not find Title Anywhere!");
	    
		ContentValues mVals = new ContentValues();
		mVals.put(Media.TITLE, Title);
		mVals.put(Media.BARCODE, UPC);
		mVals.put(Media.OWNED, 0);
		mVals.put(Media.LOANED, "");
		cr.insert(Media.CONTENT_URI, mVals);
		
		// Ok, Mobile platforms probably don't like recursion, but this really is the most graceful way to handle things...
		return MediaFactory.getMediaByUPC(mContext, UPC);
	}
	
}
