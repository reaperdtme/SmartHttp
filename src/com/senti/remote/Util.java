package com.senti.remote;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

public class Util {

	public static final String hashmapToKV(HashMap<String, String> hm) {
		Set<String> keys = hm.keySet();
		String[] strings = new String[keys.size() * 2];
		int i = 0;
		for (String key : keys) {
			strings[i * 2] = key;
			strings[i * 2 + 1] = hm.get(key);
			i++;
		}
		return kv(strings);
	}

	public static final InputStream uriToInputStream(String uri) throws FileNotFoundException {
		return new FileInputStream(uri);
	}
	
	public static final String kv(String... strings) {
		StringBuilder sb = new StringBuilder();
		boolean sign = false;
		for (String s : strings) {
			sb.append(s + (sign ? "&" : "="));
			sign = !sign;
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

}
