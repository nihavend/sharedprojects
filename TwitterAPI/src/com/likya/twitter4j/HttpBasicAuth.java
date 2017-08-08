package com.likya.twitter4j;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class HttpBasicAuth {

    public static void main(String[] args) throws Exception {

            URL url = new URL ("https://api.twitter.com/oauth2/token");
            String encoding = "aGFrYW5zYXJpYml5aWs6c2FyaWJpeWlraGFrYW4="; // Base64Encoder.encode ("test1:test1");

            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            
			
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("Authorization", "Basic " + encoding);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			String urlParameters = "grant_type=client_credentials";
			con.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			
			// Send post request
			con.setDoInput(true);
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			// wr.close();
			
			int responseCode = con.getResponseCode();
			con.getResponseMessage();
            InputStream content = null;
			try {
				content = (InputStream)con.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            BufferedReader in   = 
                new BufferedReader (new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

    }

}