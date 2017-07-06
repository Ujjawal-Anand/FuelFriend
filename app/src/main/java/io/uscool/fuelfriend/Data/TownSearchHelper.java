package io.uscool.fuelfriend.Data;

import android.content.Context;
import android.widget.Filter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.uscool.fuelfriend.model.Town;
import io.uscool.fuelfriend.model.TownSuggestion;

/**
 * Created by ujjawal on 1/7/17.
 */

public class TownSearchHelper {

    private static List<Town> mTownList = new ArrayList<>();


    // This is used to show town suggestions when user clicks on search view
    // To do => populate this from table of database to show last searched towns by user 
    private static List<TownSuggestion> mTownSuggestions =
            new ArrayList<>(Arrays.asList(
                    new TownSuggestion("Delhi"),new TownSuggestion("Mumbai"),new TownSuggestion("Kolkata"),new TownSuggestion("Chennai")));

    public interface OnFindColorsListener {
        void onResults(List<Town> results);
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<TownSuggestion> results);
    }

    public static List<TownSuggestion> getHistory(Context context, int count) {
        initTownWrapperList(context);
        List<TownSuggestion> suggestionList = new ArrayList<>();
        TownSuggestion townSuggestion;
        for (int i = 0; i < mTownSuggestions.size(); i++) {
            townSuggestion = mTownSuggestions.get(i);
            townSuggestion.setIsHistory(true);
            suggestionList.add(townSuggestion);
            if (suggestionList.size() == count) {
                break;
            }
        }
        return suggestionList;
    }

    public static void resetSuggestionsHistory() {
        for (TownSuggestion townSuggestion : mTownSuggestions) {
            townSuggestion.setIsHistory(false);
        }
    }

    public static void findSuggestions(final Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TownSearchHelper.resetSuggestionsHistory();
                List<TownSuggestion> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (int i = 0; i<mTownList.size(); i++) {
                        TownSuggestion suggestion = new TownSuggestion(mTownList.get(i).getName());
                        if (suggestion.getBody().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion);
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                Collections.sort(suggestionList, new Comparator<TownSuggestion>() {
                    @Override
                    public int compare(TownSuggestion lhs, TownSuggestion rhs) {
                        return lhs.getIsHistory() ? -1 : 0;
                    }
                });
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<TownSuggestion>) results.values);
                }
            }
        }.filter(query);

    }


    public static void findTown(Context context, String query, final OnFindColorsListener listener) {

        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                List<Town> suggestionList = new ArrayList<>();

                if (!(constraint == null || constraint.length() == 0)) {

                    for (Town town : mTownList) {
                        if (town.getName()
                                .startsWith(constraint.toString())) {

                            suggestionList.add(town);
                        }
                    }

                }

                FilterResults results = new FilterResults();
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<Town>) results.values);
                }
            }
        }.filter(query);

    }

    private static void initTownWrapperList(Context context) {

        if (mTownList.isEmpty()) {
            List<Town> townList = DatabaseHelper.getTowns(context, true);
            for(Town town: townList) {
                mTownList.add(town);
            }

        }
    }
}
