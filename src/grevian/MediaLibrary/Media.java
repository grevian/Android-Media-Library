package grevian.MediaLibrary;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class Media 
{	
    public static final Uri CONTENT_URI = Uri.parse("content://" + MediaLibrary.AUTHORITY + "/media");
    public static final Uri SEARCH_URI = Uri.parse("content://" + MediaLibrary.AUTHORITY + "/search/");
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/grevian.MediaLibrary.Media";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/grevian.MediaLibrary.Media";
    
    public static final String DEFAULT_SORT_ORDER = "lower(title) ASC";    
	public static final String BARCODE = "barcode";
	public static final String TITLE = "title";
	public static final String OWNED = "owned";	
	public static final String LOANED = "LOANED";
	
	private String Title;
	private String UPC;
	private int Owned;
	private String Loaned;
	
	private ContentResolver cr;
	
	public Media(Cursor c, ContentResolver contentResolver)
	{
		setTitle(c.getString(c.getColumnIndexOrThrow("title")));
		setUPC(c.getString(c.getColumnIndexOrThrow("barcode")));
		setOwned(c.getInt(c.getColumnIndexOrThrow("owned")));
		setLoaned(c.getString(c.getColumnIndexOrThrow("loaned")));
		cr = contentResolver;
	}
	
	public void setTitle(String title) {
		Title = title;
	}
	public String getTitle() {
		return Title;
	}
	public void setUPC(String uPC) {
		UPC = uPC;
	}
	public String getUPC() {
		return UPC;
	}
	public void setOwned(int owned) {
		Owned = owned;
	}
	public int getOwned() {
		return Owned;
	}
	public void setLoaned(String loaned) {
		Loaned = loaned;
	}
	public String getLoaned() {
		return Loaned;
	}

	public boolean isLoaned() {
		if ( Loaned == "" )
			return false;
		else
			return true;
	}

	public void save() {
		ContentValues mVals = new ContentValues();
		mVals.put(Media.TITLE, this.Title);
		mVals.put(Media.OWNED, this.Owned);
		mVals.put(Media.BARCODE, this.UPC);
		mVals.put(Media.LOANED, this.Loaned);
		cr.update(Media.CONTENT_URI, mVals, Media.BARCODE + " = " + this.UPC, null);
	}
	
}
