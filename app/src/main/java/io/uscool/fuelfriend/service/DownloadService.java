package io.uscool.fuelfriend.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.gson.JsonObject;

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
             String result = "State : " + state.getName();
             fullUrl = urlPart + state.getCode() + "?" + time;
             try {
                 result += downloadData(fullUrl);
                 bundle.putString("result", result);
                 receiver.send(STATUS_FINISHED, bundle);

             } catch (Exception e) {
                 bundle.putString(Intent.EXTRA_TEXT, e.toString());
                 receiver.send(STATUS_ERROR, bundle);
             }

         }
         Log.d(TAG, "service stopping");

    }

    private String downloadData(String requestUrl) throws IOException, DownloadException, JSONException {
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

    private String getString(InputStream inputStream) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String response = "";
        String line = "";
        while((line = reader.readLine()) != null) {
            response += line;
        }

        if(null != inputStream) {
            inputStream.close();
        }
        return getDataFromXMLString(response);
    }

    private JSONArray parseXmlToJson(String xmlString) throws JSONException {
        JSONObject mainObject = XML.toJSONObject(xmlString);
        JSONObject dataObject = mainObject.getJSONObject("markers");
        return dataObject.getJSONArray("marker");
    }

    private String getDataFromXMLString(String xmlString) throws JSONException {
        JSONObject mainObject = XML.toJSONObject(xmlString);
        JSONObject dataObject = mainObject.getJSONObject("markers");
        JSONArray jsonArray = null;
        try {
            jsonArray = dataObject.getJSONArray("marker");
        } catch (JSONException e) {
            JSONObject dataString = dataObject.getJSONObject("marker");
            return getDataFromJsonObject(dataString);
        }

//        JSONArray jsonArray = parseXmlToJson(xmlString);
        int count = 0;
        String result = "";
        for(int i = 0; i<jsonArray.length(); i++) {
            count++;
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String town = jsonObject.getString("townname");
            result += count + " " + getDataFromJsonObject(jsonObject);
//            if(town.equals(townName)) {
//                mTownSuggestion.setDieselPrice(jsonObject.getString("hsd"));
//                mTownSuggestion.setPetrolPrice(jsonObject.getString("ms"));
//                return "Diesel Price = " + mTownSuggestion.getDieselPrice() +
//                        "\nPetrol Price = " + mTownSuggestion.getPetrolPrice();
//            }

        }
        return result;
//        return jsonArray.toString();
    }

    private String getDataFromJsonObject(JSONObject dataObject) throws JSONException {
        String townName = dataObject.getString("townname");
        String petrolPrice = dataObject.getString("ms");
        String dieselPrice = dataObject.getString("hsd");
        String result = "Town : " + townName + "\n"
                + "Diesel Price : " + dieselPrice + "\n"
                + "Petrol Price : " + petrolPrice + "\n\n";
        return result;
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
