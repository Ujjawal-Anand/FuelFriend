package io.uscool.fuelfriend.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.os.ResultReceiver;
import android.util.Log;

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
import io.uscool.fuelfriend.helper.PreferenceHelper;
import io.uscool.fuelfriend.model.FuelPrice;
import io.uscool.fuelfriend.model.State;

/**
 * Created by ujjawal on 5/7/17.
 *
 */

public class DownloadService extends IntentService {
//    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;
    public static String fullUrl = "";

    private static List<FuelPrice> mFuelPriceList;


    public static final String TAG = "DownloadService";

    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

     @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if(!PreferenceHelper.isSynced(getApplicationContext())) {

            final ResultReceiver receiver = intent.getParcelableExtra("receiver");
            Bundle bundle = new Bundle();

            String urlPart = "http://hproroute.hpcl.co.in/StateDistrictMap_4/fetchmshsdprice.jsp?param=T&statecode=";
            long time = System.currentTimeMillis();
            List<State> stateList = DatabaseHelper.getStates(getApplicationContext(), true);
            mFuelPriceList = new ArrayList<>();

            for (int i = 0; i < stateList.size(); i++) {
                State state = stateList.get(i);
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

            DatabaseHelper.updateFuelPrice(getApplicationContext(), mFuelPriceList);
            PreferenceHelper.setCurrentDataDownloadDate(getApplicationContext());
            receiver.send(STATUS_FINISHED, bundle);
            Log.d(TAG, "service stopping");
        }
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

    private String getDataFromXMLString(String xmlString) 
                                               throws JSONException {
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
        String townCode = dataObject.getString("towncode");
        if(townCode.length() < 6) {
            // getString method is omitting leading 0's from certain townCodes
            // like if towncode is 055344 its reading 55344
            // this hack adds those missing 0's giving the fact that
            // length of towncode is always 6
            String strToAdd = "";
            for(int i =0 ; i < 6-townCode.length(); i++) {
                strToAdd += "0";
            }
            townCode = strToAdd+townCode;
        }
        String petrolPrice = dataObject.getString("ms");
        String dieselPrice = dataObject.getString("hsd");

        mFuelPriceList.add(new FuelPrice(townCode, dieselPrice, petrolPrice));
//        DatabaseHelper.updateFuelPrice(getApplicationContext(),
//                new FuelPrice(townCode,dieselPrice, petrolPrice));

        return  "\nTown : " + townName + "\n"
                + "Town Code : "  + townCode + "\n"
                + "Diesel Price : " + dieselPrice + "\n"
                + "Petrol Price : " + petrolPrice + "\n\n";
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private class DownloadException extends Exception {
        private DownloadException(String message) {
            super(message);
        }
       /* public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }*/
    }


}
