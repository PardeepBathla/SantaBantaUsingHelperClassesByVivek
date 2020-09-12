package com.app.santabanta.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.santabanta.R;
import com.app.santabanta.base.BaseFragment;

public class FragmentJokes extends BaseFragment {

    private static String TAG = FragmentJokes.class.getSimpleName();
    private Activity mActivity;

    public static FragmentJokes newInstance(Bundle bundle) {
        FragmentJokes myFragment = new FragmentJokes();
        myFragment.setArguments(bundle);
        return myFragment;
    }



    @Override
    public View getFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_jokes, parent, false);
        return view;
    }

}
