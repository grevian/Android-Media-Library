package grevian.MediaLibrary;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MediaLibrary extends Activity {
	
	private final static String TAG = "GrevianMedia";
	private SQLDataSource mDataSource;
	private SQLiteCursor searchCursor;
	private SQLSearchAdapter mSearchAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.i(TAG, "Application Started.");
		
		mDataSource = new SQLDataSource(this.getBaseContext());
	
		// Setup the searching field
		searchCursor = mDataSource.getTitleSearchCursor();
		this.startManagingCursor(searchCursor);
		EditText searchText = (EditText) findViewById(R.id.SearchText);
		mSearchAdapter = new SQLSearchAdapter(((ListView)findViewById(R.id.ResultList)), searchText, searchCursor);
		
		if ( savedInstanceState != null && savedInstanceState.containsKey("searchText"))
		{
			searchText.setText(savedInstanceState.getString("searchText"));
			mSearchAdapter.update();
		}
		
		// Setup the scanner
		Button mButton = (Button)findViewById(R.id.ScanButton);
		mButton.setOnClickListener(		
			new Button.OnClickListener() {
		    public void onClick(View v) {
		        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		        intent.putExtra("SCAN_MODE", "SCAN_MODE");
		        startActivityForResult(intent, 0);
		    }
		});
		
		// Set up the list to display item details when an item is selected
		ListView mList = (ListView)findViewById(R.id.ResultList);
		mList.setOnItemClickListener(
			new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> ListContents, View mView, int position, long arg3) {
				String[] itemDetails = (String[])ListContents.getItemAtPosition(position);
				Intent i = new Intent(MediaLibrary.this, grevian.MediaLibrary.ItemFoundActivity.class);
		        i.putExtra("UPC", itemDetails[2] );
		        startActivity(i);
			}
		});
		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Log.v(TAG, "onActivityResult called in ScanHandler");
	    if (requestCode == 0) {
	        if (resultCode == RESULT_OK) {
	            String contents = intent.getStringExtra("SCAN_RESULT");
	            
	            Intent i = new Intent(MediaLibrary.this, grevian.MediaLibrary.ItemFoundActivity.class);
	            i.putExtra("UPC", contents);
	            startActivity(i);
	            
	        } else if (resultCode == RESULT_CANCELED) {
	        	Log.i(TAG, "Scan Cancelled.");
	        }
	    }
	}

}
