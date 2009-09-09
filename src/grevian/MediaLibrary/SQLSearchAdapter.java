package grevian.MediaLibrary;

import android.database.sqlite.SQLiteCursor;
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
		return _cursor.getString(0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// If we already have a view component set here, just set it's text appropriately instead of recreating it
		if ( convertView != null )
		{
			((TextView)convertView).setText((String)getItem(position));
			return convertView;
		}			
		
		// Otherwise Create a textview with our value set, and return it
		TextView mText = new TextView(parent.getContext());
		mText.setText((String)getItem(position));
		return mText;
	}
 
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if ( _text.getText().toString().equalsIgnoreCase("") )
		{
			this.notifyDataSetInvalidated();
			return false;
		}
		
		// Time and execute the search query
		long sTime = System.currentTimeMillis();
		String[] Args = { "%" + _text.getText().toString() + "%" };
		_cursor.setSelectionArguments(Args);
		_cursor.requery();
		long fTime = System.currentTimeMillis();		
		Log.i(TAG, "Search for term '" + Args[0] + "' returned " + _cursor.getCount() + " results in " + (fTime-sTime) + "ms");
		
		// Probably not needed, but doesn't hurt
		_cursor.moveToFirst();

		this.notifyDataSetChanged();
		return false;
	}
}
