package proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

			//System.out.println("thread " + Thread.currentThread().getId() + "- "+command);

			if (u_cookie != null) {
				if (g_cookie != null) {
					//System.out.println("thread " + Thread.currentThread().getId() + "- "+command); 
					//System.out.println("thread " + Thread.currentThread().getId() + "- catan.game="+g_cookie);
					//System.out.println("thread " + Thread.currentThread().getId() + "- catan.user="+u_cookie);

					c.setRequestProperty("Cookie", "catan.user=" + u_cookie + "; catan.game=" + g_cookie);
				} else {
					//System.out.println("thread " + Thread.currentThread().getId() + "- catan.user="+u_cookie); 
					c.setRequestProperty("Cookie", "catan.user=" + u_cookie);
				}
			} else {
				//System.out.println("thread " + Thread.currentThread().getId() + "- NO COOKIE FOR YOU");
			}

			c.setDoOutput(true);
			c.setDoInput(true);
			c.setRequestProperty("Content-Type", "application/json");
			c.setRequestProperty("Accept", "application/json");
			c.connect();

			String get = json.toString();

			OutputStream requestBody = c.getOutputStream();
			requestBody.write(get.getBytes());
			requestBody.close();

			String new_cookie = c.getHeaderField("Set-Cookie");
			int status = c.getResponseCode();
//			System.out.println("thread " + Thread.currentThread().getId() + "- response headers");
//			System.out.println("thread " + Thread.currentThread().getId() + "- "+c.getHeaderField("cookie"));

			switch (status) {
			case 201:
			case 200:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				boolean first = true;
				while ((line = br.readLine()) != null) {
					if (first) {
						sb.append(line);
						first = false;
					} else {
						sb.append("\n" + line);
					}
				}
				br.close();

				String result = "";
				if (new_cookie != null) {
					//sb.append(cookie);
					result = sb.toString() + new_cookie;
				} else {
					result = sb.toString();
				}
				System.out.println("thread " + Thread.currentThread().getId() + "- =================== START ============================");
				System.out.println("thread " + Thread.currentThread().getId() + "- " + command);
				//System.out.println("thread " + Thread.currentThread().getId() + "- " + result);
				System.out.println("thread " + Thread.currentThread().getId() + "- ==================== END ===========================");

				return result;
			}

		} catch (MalformedURLException ex) {
			System.out.println("thread " + Thread.currentThread().getId() + "- MalformedURLException ex");
			//Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			System.out.println("thread " + Thread.currentThread().getId() + "- IOLException ex");
			//Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (c != null) {
				try {
					c.disconnect();
				} catch (Exception ex) {
					System.out.println("thread " + Thread.currentThread().getId() + "- Exception ex");
					//Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return null;
	}

}
