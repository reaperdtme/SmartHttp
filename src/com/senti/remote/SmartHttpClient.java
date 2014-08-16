package com.senti.remote;

public abstract class SmartHttpClient {
	
	public abstract String hostname();
	public abstract boolean alwaysSecure();
	
	public WebTask newTask(String relativeUrl) {
		WebTask wt = new WebTask();
		wt.url = this.fullUrlFromRelative(relativeUrl);
		return wt;
	}
	
	public String fullUrlFromRelative(String rel) {
		return this.alwaysSecure()?"https":"http" + this.hostname() + rel; 
	}

}
