package grevian.MediaLibrary;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Media 
{	
	private String Title;
	private String UPC;
	private int Owned;
	private String Loaned;
	private SQLDataSource mDataSource;
	
	public Media()
	{
		setTitle("");
		setUPC("");
		setOwned(0);
		setLoaned("");
	}
	
	public Media(String Title, String UPC, int Owned, String Loaned)
	{
		setTitle(Title);
		setUPC(UPC);
		setOwned(Owned);
		setLoaned(Loaned);
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

	public void setDataSource(SQLDataSource mDataSource) {
		this.mDataSource = mDataSource;
	}

	public void save() {
		SQLiteDatabase mInsertDB = mDataSource.getWritableDatabase();
		ContentValues mVals = new ContentValues();
		mVals.put("title", this.Title);
		mVals.put("owned", this.Owned);
		String[] wArgs = { this.UPC };
		mInsertDB.update("t_media", mVals, "barcode = ?", wArgs);
	}
	
}
