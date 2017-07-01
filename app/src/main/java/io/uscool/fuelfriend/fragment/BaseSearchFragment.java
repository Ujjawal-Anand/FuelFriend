package io.uscool.fuelfriend.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.arlib.floatingsearchview.FloatingSearchView;

/**
 * Created by ujjawal on 30/6/17.
 */

public abstract class BaseSearchFragment extends Fragment {
    private BaseSearchFragmentCallbacks mCallbacks;

    public interface BaseSearchFragmentCallbacks {
        void onAttachSearchViewToDrawer(FloatingSearchView searchView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseSearchFragmentCallbacks) {
            mCallbacks = (BaseSearchFragmentCallbacks) context;
        } else {
            throw new ClassCastException(context.toString()
            + "must implement BaseSearchFragmentCallbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    protected void attachSearchViewActivityDrawer(FloatingSearchView searchView){
        if(mCallbacks != null){
            mCallbacks.onAttachSearchViewToDrawer(searchView);
        }
    }

    public abstract boolean onActivityBackPress();
}
