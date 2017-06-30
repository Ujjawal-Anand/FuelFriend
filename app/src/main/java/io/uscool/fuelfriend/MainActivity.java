package io.uscool.fuelfriend;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mPriceView;
    private Town mTown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPriceView = (TextView) findViewById(R.id.petrol_price);
        mTown = new Town("Bihar", "Patna");

        updatePrice();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updatePrice() {
        String stateCode = mTown.getStateCode();
        String urlPart = "http://hproroute.hpcl.co.in/StateDistrictMap_4/fetchmshsdprice.jsp?param=T&statecode=";
        urlPart += stateCode;
        long time = System.currentTimeMillis();
        String fullUrl = urlPart+"?"+time;
        OkHttpHandler okHttpHandler= new OkHttpHandler();
        okHttpHandler.execute(fullUrl);
    }

    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();
        private int dieselPrice;
        private int petrolPrice;
        private int townCode;
        private int lan;
        private int lat;

        private String parseXml(String xmlString) {
            String[] separated = xmlString.split("townname=\"Patna\"");
            String petrolPrice;
            String dieselPrice;
            String subString = separated[1].split("ms=")[1];
            String[] str2 = subString.split("hsd");
            petrolPrice = str2[0];
            dieselPrice = str2[1].split("is_metro")[0];
            return "City Name = Patna \n Petrol Price = "+ petrolPrice +
                    "\n Diesel Price" + dieselPrice;

        }

        private JSONArray parseXmlToJson(String xmlString) throws JSONException {
            JSONObject mainObject = XML.toJSONObject(xmlString);
            JSONObject dataObject = mainObject.getJSONObject("markers");
            return dataObject.getJSONArray("marker");
        }

        private String getDataFromXMLString(String xmlString) throws JSONException {
            JSONArray jsonArray = parseXmlToJson(xmlString);
            for(int i = 0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String townName = jsonObject.getString("townname");
                if(townName.equals(mTown.getTownName())) {
                    mTown.setDieselPrice(jsonObject.getDouble("hsd"));
                    mTown.setPetrolPrice(jsonObject.getDouble("ms"));
                    mTown.setTownCode(jsonObject.getInt("towncode"));
                    mTown.setLat(jsonObject.getDouble("lat"));
                    mTown.setLon(jsonObject.getDouble("lng"));
                    if(jsonObject.getString("is_metro").equals("Y")) {
                        mTown.setIsMetro(true);
                    } else {
                        mTown.setIsMetro(false);
                    }
                    return "Diesel Price =" + mTown.getmDieselPrice() +
                            "\n Petrol Price =" + mTown.getPetrolPrice();
                }
            }
            return null;
        }


        @Override
        protected String doInBackground(String...params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
//                return parseXml(response.body().string());
                return getDataFromXMLString(response.body().string());

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null) {
                mPriceView.setText(s);
            }
            else {
                mPriceView.setText("Return text is null");
            }
        }
    }





}
