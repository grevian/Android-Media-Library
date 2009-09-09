package grevian.MediaLibrary;

public class Media 
{	
	private String Title;
	private String UPC;
	private int Owned;
	private String Loaned;
	
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
	
}
