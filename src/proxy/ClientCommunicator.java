package proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonObject;

public class ClientCommunicator {

	private String SERVER_HOST = "localhost";
	private int SERVER_PORT = 8081;
	private String PATH = "/user/login"; 
	private String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	
	/**
	 * description: takes an api command from the proxy or poller and sends it to the server
	 * @pre input has to be put into an input object depending on the function
	 * @post return an object that will be passed back to proxy and turned into function output object
	 */
	
	 public String send(JsonObject json, String command) {
	    HttpURLConnection c = null;
	    try {
	        URL u = new URL(URL_PREFIX + command);
	        c = (HttpURLConnection) u.openConnection();
	        c.setRequestMethod("POST");
	        //c.setRequestProperty("Content-length", "0");
	        //c.setUseCaches(false);
	        //c.setAllowUserInteraction(false);
	        //c.connect();
	        c.setDoOutput(true);
	        c.setDoInput(true);
	        c.setRequestProperty("Content-Type", "application/json");
	        c.setRequestProperty("Accept", "application/json");
	        
	        JsonObject obj = new JsonObject();
	        obj.addProperty("username", "SAM");
	        obj.addProperty("password", "sam");

	        //System.out.println(obj);
	        System.out.println(json);
	        
	        OutputStreamWriter wr = new OutputStreamWriter(c.getOutputStream());
	        wr.write(json.toString());
	        wr.flush();
	        
	        int status = c.getResponseCode();
	        //System.out.println("STATUS: "+ status); 
	        
	        switch (status) {
	            case 201:
	            case 200:
	                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
	                StringBuilder sb = new StringBuilder();
	                String line;
	                boolean first = true; 
	                while ((line = br.readLine()) != null) {
	                	if(first){
	                		sb.append(line);
	                		first = false; 
	                	}else{
	                		sb.append("\n" + line);
	                	}
	                    
	                }
	                br.close();
	                //System.out.println(sb.toString()); 
	                return sb.toString();
	        }

	    } catch (MalformedURLException ex) {
	    	System.out.println("MalformedURLException ex"); 
	        //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
	    } catch (IOException ex) {
	    	System.out.println("IOLException ex");
	        //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	       if (c != null) {
	          try {
	              c.disconnect();
	          } catch (Exception ex) {
	        	  System.out.println("Exception ex");
	        	  //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
	          }
	       }
	    }
	    return null;
	}
	 
	
}
