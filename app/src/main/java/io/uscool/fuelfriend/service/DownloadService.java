package io.uscool.fuelfriend.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.uscool.fuelfriend.Data.DatabaseHelper;
import io.uscool.fuelfriend.model.State;

/**
 * Created by ujjawal on 5/7/17.
 */

public class DownloadService extends IntentService {
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;
    public static String fullUrl = "";

    public static final String TAG = "DownloadService";


    private Context mContext;
    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

     @Override
    protected void onHandleIntent(@Nullable Intent intent) {
         final ResultReceiver receiver = intent.getParcelableExtra("receiver");
         Bundle bundle = new Bundle();

         String urlPart = "http://hproroute.hpcl.co.in/StateDistrictMap_4/fetchmshsdprice.jsp?param=T&statecode=";
         long time = System.currentTimeMillis();
         List<State> stateList = DatabaseHelper.getStates(getApplicationContext(), true);
         for(State state : stateList) {
             fullUrl = urlPart + state.getCode() + "?" + time;
             try {
                 String result = downloadData(fullUrl);
                 bundle.putString("result", result);
                 receiver.send(STATUS_FINISHED, bundle);

             } catch (Exception e) {
                 bundle.putString(Intent.EXTRA_TEXT, e.toString());
                 receiver.send(STATUS_ERROR, bundle);
             }

         }
         Log.d(TAG, "service stopping");

    }

    private String downloadData(String requestUrl) throws IOException, DownloadException {
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        URL url = new URL(requestUrl);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestProperty("Content-Type", "application/xml");
        urlConnection.setRequestProperty("Accept", "application/xml");
        urlConnection.setRequestMethod("GET");

        int statusCode = urlConnection.getResponseCode();
        if(statusCode == 200 ) {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            String response = getString(inputStream);
            return response;
        } else {
            throw new DownloadException("Failed to fetch data");
        }
    }

    private String getString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String response = "";
        String line = "";
        while((line = reader.readLine()) != null) {
            response += line;
        }

        if(null != inputStream) {
            inputStream.close();
        }
        return response;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public class DownloadException extends Exception {
        public DownloadException(String message) {
            super(message);
        }
        public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }
    }


}
