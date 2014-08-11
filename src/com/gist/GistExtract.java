package com.gist;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class GistExtract {

	
	public static final  String GIT_URL ="https://api.github.com/users/KodeSeeker/gists";
	public static final  String workSpacePath="/Users/KodeSeeker/Personal/git_repo/"; 
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	
	public static void JSONExtract()
	{
	try
	{
	URL url = new URL(GIT_URL);
	
	 InputStream is = url.openStream();
	 BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
     String jsonText = readAll(rd);
     JSONArray jsArray = new JSONArray(jsonText); 
   
	
	for(int i=0;i<jsArray.length();i++)
	{
		JSONObject jsOb= jsArray.getJSONObject(i);
		JSONObject jsChildOb=(JSONObject)jsOb.get("files");

		Iterator<String> iterator  = jsChildOb.keys();
		String key = null;
		while(iterator.hasNext()){
            key = (String)iterator.next();
          
            String fname = (String)((JSONObject)jsChildOb.get(key)).get("filename");
            FileWriter fw= new FileWriter(workSpacePath+fname);
            URL rawURL= new URL((String)((JSONObject)jsChildOb.get(key)).get("raw_url"));
            InputStream in= rawURL.openStream();
            BufferedReader buff= new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
            String rawCode=readAll(buff);
            System.out.println(rawCode);
            fw.write(rawCode);
            fw.close();
   
          
		}
		
	}
	}
	
	
	catch(MalformedURLException mal)
	{
		mal.printStackTrace();
	}
	
	catch(IOException io)
	{
		io.printStackTrace();
	} 
	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	

	public static void main (String... args)
	{
		JSONExtract();
	}
	
}
