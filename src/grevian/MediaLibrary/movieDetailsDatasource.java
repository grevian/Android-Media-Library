package grevian.MediaLibrary;

import java.util.HashMap;
import org.xmlrpc.android.XMLRPCClient;
import android.util.Log;

public class movieDetailsDatasource {
	@SuppressWarnings("unchecked")
	public static String getDetailsByTitle(String title)
	{
	    String text = "";
	    try
	    {
	    	XMLRPCClient client = new XMLRPCClient("http://api.opensubtitles.org/xml-rpc");
	    	
	    	HashMap<String, String> loginResults = (HashMap<String, String>)client.call("LogIn", "", "", "CA", "GrevianMedia 0.1");
	    	String sessionToken = loginResults.get("token");
	    	
	    	String[][] args = { {"query", title} };
	    	HashMap<String, String> results = (HashMap<String, String>)client.call("SearchSubtitles", sessionToken, args);
	    	
	        if  ( results.size() > 0 &&
	            results.get("message").equalsIgnoreCase("Database entry found"))
	        {
	        	// TODO: Do a query to get imdb details and shuffle them into an appropriate data structure
	            text = results.get("description");
	        }
	        
	        client.call("LogOut", sessionToken);
	        
	    }
	    catch (Exception e)
	    {
	    	Log.e("GrevianMedia", e.getMessage());
	    	return "";
	    }
	    
	    return text;
	}
}
