package com.senti.remote;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONException;

import android.os.Handler;
import android.os.Looper;

public class Runner {

	private ExecutorService e;
	private static Runner r;
	private Handler mainHandler = new Handler(Looper.getMainLooper());

	public static void go(final WebTask run) {
		get().go_impl(run);
	}

	private void go_impl(final WebTask run) {
		e.execute(new Runnable() {

			@Override
			public void run() {
				run.onDataListener.onData(run);
				if (run.onCompleteListener != null)
					mainHandler.post(new Runnable() {
						@Override
						public void run() {
							run.onCompleteListener.onComplete(run);
						}
					});
			}

		});
	}

	public Future<?> runTask(Runnable r) {
		return e.submit(r);
	}

	private Runner() {
		e = Executors.newCachedThreadPool();
	}

	public static Runner get() {
		if (r == null)
			r = new Runner();
		return r;
	}

}
