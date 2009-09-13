package grevian.MediaLibrary;

import android.database.sqlite.SQLiteCursor;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SQLSearchAdapter extends BaseAdapter implements OnKeyListener {
	
	private final static String TAG = "GrevianMedia";
	private ListView _list;
	private EditText _text;
	private SQLiteCursor _cursor;
	
	public SQLSearchAdapter(ListView mList, EditText mText, SQLiteCursor query)
	{	
		_list = mList;
		_text = mText;
		_cursor = query;
		String[] Args = { _text.getText().toString() };
		_cursor.setSelectionArguments(Args);
		_cursor.requery();
		_text.setOnKeyListener(this);
		_list.setAdapter(this);
	}
	
	@Override
	public int getCount() {
		return _cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		_cursor.moveToPosition(position);
		String[] Results = { _cursor.getString(0), _cursor.getString(2), _cursor.getString(1) };
		return Results;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// if the existing view is null, create it as a textview
		if ( convertView == null )
			convertView = new TextView(parent.getContext());
		
		TextView mText = (TextView)convertView;
		String[] vals = (String[])getItem(position);
		mText.setText(vals[0]);
		
		if ( Integer.valueOf(vals[1]) > 0 )
			mText.setTextColor(Color.GREEN);
		else
			mText.setTextColor(Color.RED);
				
		return mText;
	}
	
	public boolean update()
	{
		// Time and execute the search query
		long sTime = System.currentTimeMillis();

		String[] Args;

		// FIXME: Uuuugggghhh.
		if ( _text.getText().toString().equalsIgnoreCase("") )
			Args = new String[] { "QQQQQQQQQ" };
		else
			Args = new String[] { "%" + _text.getText().toString() + "%" };
		
		_cursor.setSelectionArguments(Args);
		_cursor.requery();
		// Probably not needed, but doesn't hurt
		_cursor.moveToFirst();	
		
		long fTime = System.currentTimeMillis();		
		Log.i(TAG, "Search for term '" + Args[0] + "' returned " + _cursor.getCount() + " results in " + (fTime-sTime) + "ms");

		this.notifyDataSetChanged();
		return true;
	}
 
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		update();
		return false;
	}
}
