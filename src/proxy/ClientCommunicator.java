package proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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
	
	 public String send(JsonObject json, String command, String u_cookie, String g_cookie) {
	    HttpURLConnection c = null;
	    try {
	        URL u = new URL(URL_PREFIX + command);
	        c = (HttpURLConnection) u.openConnection();
	        c.setRequestMethod("POST");
	        //c.setRequestProperty("Content-length", "0");
	        //c.setUseCaches(false);
	        //c.setAllowUserInteraction(false);
	        if(u_cookie != null){
	        	//System.out.println("setting cookie"); 
	        	//System.out.println("catan.user="+u_cookie);
	        	if(g_cookie !=null)
	        	{
	        		//System.out.println("setting combined cookie");
	        		c.setRequestProperty("Cookie", "catan.user="+u_cookie+"; catan.game="+g_cookie);
	        	}
	        	else
	        	{
	        		c.setRequestProperty("Cookie", "catan.user="+u_cookie);
	        	}
	        }
//	        if(command == "/games/join"){
//	        	System.out.println(cookie); 
//	        	c.setRequestProperty("Cookie", "catan.user=%7B%22authentication%22%3A%222680927%22%2C%22name%22%3A%22SAM%22%2C%22password%22%3A%22sam%22%2C%22playerID%22%3A12%7D");
//	        }
	        
	        //    set the cookie setRequestProperty("Cookie", cookie); 
	        //c.connect();
	        
	        // get game cookie in game join?
	        
	        c.setDoOutput(true);
	        c.setDoInput(true);
	        c.setRequestProperty("Content-Type", "application/json");
	        c.setRequestProperty("Accept", "application/json");
	        
	        c.connect();
	      
	        //System.out.println(json);
//	        OutputStreamWriter wr = new OutputStreamWriter(c.getOutputStream());
//	        wr.write(json.toString());
//	        wr.flush();
//	        
	        String get = json.toString();
	        
	        OutputStream requestBody = c.getOutputStream();
	        requestBody.write(get.getBytes());
	        requestBody.close();
	        
	        
	        
	        
//	        String headerName = c.getHeaderFieldKey(0);
//	        String headerValue = c.getHeaderField(0);
	        //System.out.println(c.getHeaderFields());
	        String new_cookie = c.getHeaderField("Set-Cookie");
//	        System.out.println("headerName:");
//	        System.out.println(headerName);
//	        System.out.println("headerValue:");
//	        System.out.println(headerValue);
//	        System.out.println("Set-Cookie");
	        //System.out.println(new_cookie);
	        
	        
	        
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
	                
	        		
	                if(new_cookie != null)
	                {
	                	//sb.append(cookie);
	                	return sb.toString()+new_cookie;
	                }else{
	                	return sb.toString();
	                }
	                
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
