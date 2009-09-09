package grevian.MediaLibrary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ItemFoundActivity extends Activity 
{
	private final static String TAG = "GrevianMedia";
	private Media mMedia;
	private Bundle extras;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		extras = getIntent().getExtras();
		
		try {
			mMedia = MediaFactory.getMediaByUPC(this.getBaseContext(), extras.getString("UPC"));
		}
		catch (LookupException e )
		{
			Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
        	toast.show();
        	this.finish();
		}
				
		
       	TextView titleText = (TextView)findViewById(R.id.TitleText);
   		titleText.setText(mMedia.getTitle());
		
   		/*
		TextView ownedText = (TextView)findViewById(R.id.CopiesText);
		ownedText.setText(mMedia.getOwned());
   		 */
   		
		TextView loanedText = (TextView)findViewById(R.id.LoanedText);
		loanedText.setText(mMedia.getLoaned());

	}
	
}
