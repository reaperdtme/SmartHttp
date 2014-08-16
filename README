SmartHttp
=========

Smart Http is an easy, effective android http library.

  - Uses simple callbacks
  - Provides callback in background thread for handling of data received from server (good for parsing, databasing, file processing, etc)
  - Provides callback on main thread for updating UI *after data is ready*.
  - Simple client extension to implement
  - Supports https and file uploads

Available at:

  - Senti Repo: http://git.senti.in/senti/smarthttp
  - Github Repo: https://github.com/zedknight/SmartHttp

Usage
--------------

Extend the SmartHttpClient and override the abstracts

```java
public class SentiHttp extends SmartHttpClient {
	
	@Override
	public String hostname() {
		return "senti.in";
	}
	
	@Override
	public boolean alwaysSecure() {
		return false;
	}

}

```

Add methods to your client for each API like so:

```java
public class SentiHttp extends SmartHttpClient {
	
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
```
The Runner will automatically handle calling your OnData and OnComplete listeners. 

Now all you need to do is call your Client from your activity:

```java
public class SampleActivity extends Activity {

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

```

To-Do
----
  - Image downloads
  - File downloads
  - LRU cache
  - Customizable ThreadPools


License
----

MIT


**Free Software, Hell Yeah!**