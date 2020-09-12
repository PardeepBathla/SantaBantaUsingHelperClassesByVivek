package com.app.santabanta.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.santabanta.R;
import com.app.santabanta.base.BaseFragment;

public class FragmentMemes extends BaseFragment {

    private static String TAG = FragmentMemes.class.getSimpleName();
    private Activity mActivity;

    public static FragmentMemes newInstance(Bundle bundle) {
        FragmentMemes myFragment = new FragmentMemes();
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public View getFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_memes, parent, false);
        return view;
    }

}
