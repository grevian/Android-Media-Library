package grevian.MediaLibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        	return;
		}
		
       	TextView titleText = (TextView)findViewById(R.id.TitleText);
   		titleText.setText(String.valueOf(mMedia.getTitle()));
		
		final TextView ownedText = (TextView)findViewById(R.id.CopiesText);
		ownedText.setText(Integer.toString(mMedia.getOwned()));
   		
   		if ( mMedia.getLoaned() != "" )
   		{
   			TextView loanedText = (TextView)findViewById(R.id.LoanedText);
   			loanedText.setText(mMedia.getLoaned());
   		}
		
		// Set up the button to add copies
		Button mButton = (Button)findViewById(R.id.AddButton);
		mButton.setOnClickListener(		
			new Button.OnClickListener() {
		    public void onClick(View v) {
		        mMedia.setOwned(mMedia.getOwned()+1);
		        mMedia.save();
		        ownedText.setText(Integer.toString(mMedia.getOwned()));
		    }
		});

	}
	
}
