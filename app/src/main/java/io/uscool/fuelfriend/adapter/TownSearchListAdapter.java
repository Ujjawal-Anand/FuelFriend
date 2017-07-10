package io.uscool.fuelfriend.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.arlib.floatingsearchview.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.uscool.fuelfriend.Data.DatabaseHelper;
import io.uscool.fuelfriend.R;
import io.uscool.fuelfriend.model.Town;


/**
 * Created by ujjawal on 1/7/17.
 *
 */

public class TownSearchListAdapter extends RecyclerView.Adapter<TownSearchListAdapter.ViewHolder>{

    public static final String IS_DIESEL = "0";
    public static final String IS_PETROL = "1";

    private List<Town> mDataSet = new ArrayList<>();

    private int mLastAnimatedItemPosition = -1;

    private static TextView mDieselPrice;
    private static TextView mPetrolPrice;
    private Town mTownSuggestion;
    private static Context mContext;

    public interface OnItemClickListener{
        void onClick(Town colorWrapper);
    }

    public TownSearchListAdapter(Context context) {
        this.mContext = context;
    }


    private OnItemClickListener mItemsOnClickListener;

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTownName;
//        public final TextView mDieselPrice;
//        public final TextView mPetrolPrice;
        private final View mTextContainer;

        private ViewHolder(View view) {
            super(view);

            mTownName = (TextView) view.findViewById(R.id.town_name);
            mDieselPrice = (TextView) view.findViewById(R.id.diesel_price);
            mPetrolPrice = (TextView) view.findViewById(R.id.petrol_price);
            mTextContainer = view.findViewById(R.id.text_container);

        }
    }

    public void swapData(List<Town> mNewDataSet) {
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
//        String statecode = DatabaseHelper.getStates(mContext, true).get(0).getCode();
//           had created just to check newly created StateTable is working or not, it's working, Yay :D
        updatePrice(mTownSuggestion.getCode(), IS_DIESEL);
        updatePrice(mTownSuggestion.getCode(), IS_PETROL);
//        updatePrice(statecode, mTownSuggestion.getTownName());
        holder.mTownName.setText(mTownSuggestion.getName());
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

    private void updatePrice(String townCode, String fuelType) {
        DataHandler dataHandler= new DataHandler();
        dataHandler.execute(townCode, fuelType);
    }

    public static class DataHandler extends AsyncTask<String, Void, String> {
        boolean isDiesel;
        @Override
        protected String doInBackground(String...params) {
            String townCode = params[0];
            String fuelType = params[1];
            isDiesel = fuelType.equals(IS_DIESEL);
            String preString = isDiesel?"Diesel Price : ": "Petrol Price : ";
            try {
            return preString + DatabaseHelper.getCurrentFuelPriceForGiven(mContext, townCode,
                    isDiesel, null);

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null) {
                if(isDiesel) {
                    mDieselPrice.setText(s);
                }
                else {
                    mPetrolPrice.setText(s);
                }
            }
            else {
                mDieselPrice.setText("No Data Available");
            }
        }
    }


}
