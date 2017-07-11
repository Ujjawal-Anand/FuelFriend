package io.uscool.fuelfriend.service;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.uscool.fuelfriend.Data.DatabaseHelper;
import io.uscool.fuelfriend.activity.MainActivity;
import io.uscool.fuelfriend.model.FuelPrice;
import io.uscool.fuelfriend.model.State;

/**
 * Created by ujjawal on 11/7/17.
 *
 */

public class MySimpleService extends IntentService {
    public static final int NOTIF_ID = 56;
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    long timestamp;
    private static List<FuelPrice> mFuelPriceList;

    // Must create a default constructor
    public MySimpleService() {
        super("simple-service");
    }

    // This describes what will happen when service is triggered
    @Override
    protected void onHandleIntent(Intent intent) {
        timestamp =  System.currentTimeMillis();
        // Extract additional values from the bundle
        String val = intent.getStringExtra("foo");
        // Extract the receiver passed into the service
        ResultReceiver rec = intent.getParcelableExtra("receiver");
        // Sleep a bit first
        sleep(3000);
        initDownload(rec);
        // Let's also create notification
        createNotification(val);
    }

    private void initDownload(ResultReceiver receiver) {
        String urlPart = "http://hproroute.hpcl.co.in/StateDistrictMap_4/fetchmshsdprice.jsp?param=T&statecode=";
        long time = System.currentTimeMillis();
        List<State> stateList = DatabaseHelper.getStates(getApplicationContext(), true);
        mFuelPriceList = new ArrayList<>();
        String fullUrl;
        Bundle bundle = new Bundle();

        try {
            for(int i = 0; i< stateList.size(); i++) {
            State state = stateList.get(i);
            fullUrl = urlPart + state.getCode() + "?" + time;
            downloadData(fullUrl);
            receiver.send(STATUS_RUNNING, bundle);
            }
            DatabaseHelper.updateFuelPrice(getApplicationContext(), mFuelPriceList);
            receiver.send(STATUS_FINISHED, bundle);

        } catch (Exception e) {
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, bundle);
        }

        receiver.send(STATUS_FINISHED, bundle);
    }

    private void downloadData(String requestUrl) throws IOException, DownloadException, JSONException{
        InputStream inputStream;
        HttpURLConnection urlConnection;

        URL url = new URL(requestUrl);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestProperty("Content-Type", "application/xml");
        urlConnection.setRequestProperty("Accept", "application/xml");
        urlConnection.setRequestMethod("GET");

        int statusCode = urlConnection.getResponseCode();
        if(statusCode == 200 ) {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            getString(inputStream);

        } else {
            throw new DownloadException("Failed to fetch data");
        }

    }

    private void getString(InputStream inputStream) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        if(null != inputStream) {
            inputStream.close();
        }
        getDataFromXMLString(reader.toString());
    }

    private void getDataFromXMLString(String xmlString)
            throws JSONException {
        JSONObject mainObject = XML.toJSONObject(xmlString);
        JSONObject dataObject = mainObject.getJSONObject("markers");
        JSONArray jsonArray = null;
        try {
            jsonArray = dataObject.getJSONArray("marker");
        } catch (JSONException e) {
            JSONObject dataString = dataObject.getJSONObject("marker");
            getDataFromJsonObject(dataString);
        }

        for(int i = 0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            getDataFromJsonObject(jsonObject);
        }
    }

    private void getDataFromJsonObject(JSONObject dataObject) throws JSONException {
        String townCode = dataObject.getString("towncode");
        if(townCode.length() < 6) {
            String strToAdd = "";
            for(int i =0 ; i < 6-townCode.length(); i++) {
                strToAdd += "0";
            }
            townCode = strToAdd+townCode;
        }
        String petrolPrice = dataObject.getString("ms");
        String dieselPrice = dataObject.getString("hsd");

        mFuelPriceList.add(new FuelPrice(townCode, dieselPrice, petrolPrice));
    }

    // Construct compatible notification
    private void createNotification(String val) {
        // Construct pending intent to serve as action for notification item
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("message", "Launched via notification with message: " + val + " and timestamp " + timestamp);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // Create notification
        String longText = "Intent service has a new message with: " + val + " and a timestamp of: " + timestamp;
        Notification noti =
                new NotificationCompat.Builder(this)
                        .setContentTitle("New Result!")
                        .setContentText("Simple Intent service has a new message")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(longText))
                        .setContentIntent(pIntent)
                        .build();

        // Hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(NOTIF_ID, noti);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
