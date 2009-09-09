package grevian.MediaLibrary;

import java.util.HashMap;
import org.xmlrpc.android.XMLRPCClient;
import android.util.Log;

public class UPCDataSource {
	
	@SuppressWarnings("unchecked")
	public static String getUPCText(String upc)
	{
	    String text = "";
	    try
	    {
	    	XMLRPCClient client = new XMLRPCClient("http://www.upcdatabase.com/rpc");
	    	HashMap<String, String> results = (HashMap<String, String>)client.call("lookupUPC", upc);
	    	
	        if  ( results.size() > 0 &&
	            results.get("message").equalsIgnoreCase("Database entry found"))
	        {
	            text = results.get("description");
	        }
	    }
	    catch (Exception e)
	    {
	    	Log.e("GrevianMedia", e.getMessage());
	    	return "Not Found.";
	    }
	    return text;
	}


	
}
