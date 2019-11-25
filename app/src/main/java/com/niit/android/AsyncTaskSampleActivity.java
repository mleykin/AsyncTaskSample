package com.niit.android;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AsyncTaskSampleActivity extends Activity {
	MyTask mt;
	TextView tvInfo;
	String[] ls = new String[20];

	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    tvInfo = (TextView) findViewById(R.id.tvInfo);
	  }

	  public void onclick(View v) {
		  switch (v.getId()) {
			  case R.id.bStart:
			 	 mt = new MyTask();
			  	for (int i = 0; i < 20; i++) {
					  ls[i] = "file" + i;
			 	 }
			 	 mt.execute(ls);
			  	 break;
			  case R.id.bCancel:
				  mt.cancel(true);
		  }
	  }

	  class MyTask extends AsyncTask<String, Integer, String> {

	    @Override
	    protected void onPreExecute() {
	      super.onPreExecute();
	      tvInfo.setText("Begin");
	    }

	    @Override
	    protected String doInBackground(String... urls) {
	      try {
	        int cnt = 0;
	        for (String url : urls) {
	          downloadFile(url);
	          publishProgress(++cnt);
	        }
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	      return "Success";
	    }


	    @Override
	    protected void onProgressUpdate(Integer... values) {
	      super.onProgressUpdate(values);
	      tvInfo.setText("Downloaded " + values[0] + " files");
	    }

	    @Override
	    protected void onPostExecute(String result) {
	    	try  {
	    		super.onPostExecute(result);
	    		tvInfo.setText("End, result = " + result);
	    		String getResult = mt.get();
	    		Toast.makeText(getApplicationContext(), "get returns " + getResult, Toast.LENGTH_LONG).show();
	    	}
	    	catch (InterruptedException ex)
	    	{
	    		ex.printStackTrace();
	    	}
	    	catch (ExecutionException ex)
	    	{
	    		ex.printStackTrace();
	    	}
	    }


	    private void downloadFile(String url) throws InterruptedException {
	      Thread.sleep(2000);
	    }
	  }
}