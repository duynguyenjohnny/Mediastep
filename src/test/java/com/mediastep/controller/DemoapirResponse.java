package test.java.com.mediastep.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class DemoapirResponse {
	
	private final String USER_AGENT="Mozilla/5.0";
	public String GetResponse() throws ClientProtocolException, IOException
	{
		StringBuffer result=new StringBuffer();
		HttpClient client=new DefaultHttpClient();
		String url="http://192.168.1.107:9080/api/account";
		HttpGet request=new HttpGet(url);
		//request.addHeader("User-Agent",USER_AGENT);
		request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTQ4NTU2ODYwMn0.hVf1fAYIriyurnT2DCq8eCmhZlnUFJoK7RnZ5WIphA-tmRIxWFrsOX6M-G1gy2o5OzgDHsdXcjTa_9TJQV17bQ");
		HttpResponse response=client.execute(request);
		int responseCode=response.getStatusLine().getStatusCode();
		System.out.println("Response Code: " + responseCode);
		try{
		if(responseCode==200)
			
		{
			System.out.println("Get Response is Successfull");
			BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line="";
			while((line=reader.readLine())!=null)
			{
				result.append(line);
				System.out.println(result.toString());
			}
		}
		return result.toString();
		
	}
		catch(Exception ex)
	{
		result.append("Get Response Failed");
		return result.toString();
	}

}
}
