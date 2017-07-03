package io.uscool.fuelfriend.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.arlib.floatingsearchview.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;

import io.uscool.fuelfriend.MainActivity;
import io.uscool.fuelfriend.R;
import io.uscool.fuelfriend.model.TownWrapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ujjawal on 1/7/17.
 */

public class TownSearchListAdapter extends RecyclerView.Adapter<TownSearchListAdapter.ViewHolder>{
    private List<TownWrapper> mDataSet = new ArrayList<>();

    private int mLastAnimatedItemPosition = -1;
    private static TextView mDieselPrice;
    private static TextView mPetrolPrice;
    private TownWrapper mTownSuggestion;

    public interface OnItemClickListener{
        void onClick(TownWrapper colorWrapper);
    }

    private OnItemClickListener mItemsOnClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTownName;
//        public final TextView mDieselPrice;
//        public final TextView mPetrolPrice;
        public final View mTextContainer;

        public ViewHolder(View view) {
            super(view);

            mTownName = (TextView) view.findViewById(R.id.town_name);
            mDieselPrice = (TextView) view.findViewById(R.id.diesel_price);
            mPetrolPrice = (TextView) view.findViewById(R.id.petrol_price);
            mTextContainer = view.findViewById(R.id.text_container);

        }
    }

    public void swapData(List<TownWrapper> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }

    public void setItemsOnClickListener(OnItemClickListener onClickListener){
        this.mItemsOnClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        mTownSuggestion = mDataSet.get(position);
        updatePrice(mTownSuggestion.getStateCode(), mTownSuggestion.getTownName());
        holder.mTownName.setText(mTownSuggestion.getTownName());
//        mDieselPrice.setText(townSuggestion.getStateName());
//        mPetrolPrice.setText(townSuggestion.getStateCode());

        if(mLastAnimatedItemPosition < position){
            animateItem(holder.itemView);
            mLastAnimatedItemPosition = position;
        }

        if(mItemsOnClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemsOnClickListener.onClick(mDataSet.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private void animateItem(View view) {
        view.setTranslationY(Util.getScreenHeight((Activity) view.getContext()));
        view.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .start();
    }

    protected void updatePrice(String stateCode, String townName) {
        String urlPart = "http://hproroute.hpcl.co.in/StateDistrictMap_4/fetchmshsdprice.jsp?param=T&statecode=";
        urlPart += stateCode;
        long time = System.currentTimeMillis();
        String fullUrl = urlPart+"?"+time;
        OkHttpHandler okHttpHandler= new OkHttpHandler();
        okHttpHandler.execute(fullUrl, townName);
    }

    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();
        private int dieselPrice;
        private int petrolPrice;
        private int townCode;
        private int lan;
        private int lat;

        private JSONArray parseXmlToJson(String xmlString) throws JSONException {
            JSONObject mainObject = XML.toJSONObject(xmlString);
            JSONObject dataObject = mainObject.getJSONObject("markers");
            return dataObject.getJSONArray("marker");
        }

        private String parseXml(String xmlString, String townName) {
            String[] separated = xmlString.split("townname=\"" + townName + "\"");
            String petrolPrice;
            String dieselPrice;
            String subString = separated[1].split("ms=")[1];
            String[] str2 = subString.split("hsd");
            petrolPrice = str2[0];
            dieselPrice = str2[1].split("is_metro")[0];
            return "Petrol Price = "+ petrolPrice +
                    "\nDiesel Price" + dieselPrice;

        }

        private String getDataFromXMLString(String xmlString, String townName) throws JSONException {
            JSONArray jsonArray = parseXmlToJson(xmlString);
            for(int i = 0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String town = jsonObject.getString("townname");
                if(town.equals(townName)) {
                    mTownSuggestion.setDieselPrice(jsonObject.getString("hsd"));
                    mTownSuggestion.setPetrolPrice(jsonObject.getString("ms"));
                    return "Diesel Price = " + mTownSuggestion.getDieselPrice() +
                            "\nPetrol Price = " + mTownSuggestion.getPetrolPrice();
                }
            }
            return null;
        }


        @Override
        protected String doInBackground(String...params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            String townName = params[1];
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return parseXml(response.body().string(), townName);
//                return getDataFromXMLString(response.body().string(), townName);

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null) {
                mDieselPrice.setText(s);
            }
            else {
                mDieselPrice.setText("Return text is null");
            }
        }
    }


}
