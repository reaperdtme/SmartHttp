package com.senti.sample;

import android.util.Log;

import com.senti.remote.Runner;
import com.senti.remote.SmartHttpClient;
import com.senti.remote.WebTask;

public class SentiHttp extends SmartHttpClient {
	
	@Override
	public String hostname() {
		return "senti.in";
	}
	
	@Override
	public boolean alwaysSecure() {
		return false;
	}
	
	public void getUser(int userId, WebTask.OnCompleteListener onComplete) {
		WebTask wt = this.newTask("/user");
		wt.onCompleteListener = onComplete;
		wt.onDataListener = new WebTask.OnDataListener() {
			
			@Override
			public void onData(WebTask dataTask) {
				//Play with your JSON data:
				dataTask.getJSON();
				//or String data
				dataTask.getString();
				//or Response Code
				Log.v("SentiHttp", "Get User RC: " + dataTask.responseCode);
				
				//Do any other background tasks like databasing, file processing, etc
			}
		};
		
		//Submit WebTask to Runner
		Runner.go(wt);
	}

}
