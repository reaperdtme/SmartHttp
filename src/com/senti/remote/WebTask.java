package com.senti.remote;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.protocol.HTTP;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

public class WebTask {

	public int id = -1;
	public String url = null;
	public boolean https = false;
	public HashMap<String, String> query = null;
	public int method = Method.GET;
	public HashMap<String, String> jquery = null;
	public int responseCode;
	public String responseString;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
	public OnCompleteListener onCompleteListener;
	public OnDataListener onDataListener;
	private boolean executed = false;
	public HashMap<String,MimeObject> mime = null;
	public static JsonFactory jf;
	public JsonParser responseParser;
	Bundle competedData;
	
	
	public WebTask() {
	}
	public static JsonFactory jsFactory() {
		if(jf == null)
			jf = new JsonFactory();
		return jf;
	}

	public String getString() {
		if (responseString != null)
			return responseString;
		else if (!executed)
			connect();
		return responseString;
	}

	public JsonParser getJSON() {
		if (responseParser != null)
			return responseParser;
		else if (!executed)
			connect();
		return responseParser;
	}

	public void connect() {
		try {
			HttpURLConnection u = (HttpURLConnection) new URL(url())
					.openConnection();
			
			if (method == Method.POST && (query != null || !jquery.isEmpty() || !mime.isEmpty())) {
				u.setDoOutput(true);
				BufferedOutputStream os = new BufferedOutputStream(
						u.getOutputStream());
				if (jquery != null) {
					// Post JSON
					u.setRequestProperty("Content-Type", "application/json");
					JsonGenerator jg = jsFactory().createGenerator(os, JsonEncoding.UTF8);
					jg.writeStartObject();
					for(Map.Entry<String, String> entry : jquery.entrySet()) {
						try {
							jg.writeFieldName(entry.getKey());
							jg.writeString(entry.getValue());
						} catch(Exception e) {
						}
					}
					jg.writeEndObject();
				} else if (query != null) {
					// Post hashmap
					os.write(Util.hashmapToKV(query).getBytes());
				} else if (mime != null) {
					// Set up HTTP header for multipart and mixed content
					u.setRequestProperty(HTTP.CONTENT_TYPE, "multipart/mixed; boundary=jonisabaus");
					// Write MIME
					InputStream is;
					int intbit;
					ByteBuffer b = ByteBuffer.allocate(4);
					for(Map.Entry<String, MimeObject> entry : mime.entrySet()) {
						// Write body
						try {
							is = Util.uriToInputStream(entry.getValue().uri);
							// Write header
							os.write(new String("\n\n").getBytes());
							intbit = 0;
							while((intbit = is.read()) > -1) {
								// Transform and write bytes
								os.write(Base64.encode(b.putInt(intbit).array(), Base64.DEFAULT));							
							}
						} catch (FileNotFoundException e) {
							continue;
						}
						// Write boundary
						os.write(new String("\n--jonisabaus").getBytes());
					}
					// Write double dash for last entry
					os.write(new String("--").getBytes());
					
				}
				os.flush();
			}
			responseCode = u.getResponseCode();
			InputStream is = u.getInputStream();
			responseParser = jsFactory().createParser(is);
			onDataListener.onData(this);
			u.disconnect();
		} catch (Exception e) {
			Log.e("SData-WebTask", "Fail Connect: " + e);
		}
		executed = true;
	}

	private String url() {
		String ret = https ? "https://" : "http://" + url;
		if (method == Method.GET && query != null && query.size() > 0) {
			ret += "?";
			int i = 0, l = query.size();
			for (String k : query.keySet()) {
				ret += k + "=" + query.get(k);
				i++;
				if (i < l) {
					ret += "&";
				}
			}
		}
		return ret;
	}
	
	public void put(String key, String v) {
		jquery.put(key, v);
	}

	public class Method {
		public static final int GET = 0, POST = 1;
	}

	public interface OnCompleteListener {
		public void onComplete(WebTask completedTask);
	}
	
	public interface OnDataListener {
		public void onData(WebTask dataTask);
	}
}
