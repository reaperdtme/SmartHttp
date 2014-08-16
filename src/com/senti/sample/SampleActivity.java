package com.senti.sample;

import android.app.Activity;
import android.os.Bundle;

import com.senti.remote.Runner;
import com.senti.remote.WebTask;
import com.senti.remote.WebTask.Method;

public class SampleActivity extends Activity {

	@Override
	public void onCreate(Bundle b) {
	}

	public void onClickUser() {
		int userId = 8384;
		SentiHttp client = new SentiHttp();
		client.getUser(userId, new WebTask.OnCompleteListener() {
			
			@Override
			public void onComplete(WebTask completedTask) {
				displayUser();
			}
			
		});
	}

	public void displayUser() {

	}

}
