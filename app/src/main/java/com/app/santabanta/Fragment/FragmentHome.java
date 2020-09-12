package com.app.santabanta.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.santabanta.R;
import com.app.santabanta.base.BaseFragment;

public class FragmentHome extends BaseFragment {

    private static String TAG = FragmentHome.class.getSimpleName();
    private Activity mActivity;


    public static FragmentHome newInstance(Bundle bundle) {
        FragmentHome myFragment = new FragmentHome();
        myFragment.setArguments(bundle);
        return myFragment;
    }


    @Override
    public View getFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_home, parent, false);
        return view;
    }

}
