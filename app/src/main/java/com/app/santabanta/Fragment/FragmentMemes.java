package com.app.santabanta.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.santabanta.Helper.FragmentMemesHelper;
import com.app.santabanta.R;
import com.app.santabanta.Utils.MemesExoPlayerRecyclerView;
import com.app.santabanta.base.BaseFragment;

import butterknife.ButterKnife;

public class FragmentMemes extends BaseFragment {
    public static boolean IS_FROM_MENU = false;
    public static String subcat_slug_name;
    private static String TAG = FragmentMemes.class.getSimpleName();
    private Activity mActivity;
    private FragmentMemesHelper mHelper;


    public static FragmentMemes newInstance(Bundle bundle) {
        FragmentMemes myFragment = new FragmentMemes();
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public View getFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_memes, parent, false);
        ButterKnife.bind(this, view);
        initFragment(view);
        return view;
    }

    private void initFragment(View view) {
        mHelper = new FragmentMemesHelper(getActivity(),FragmentMemes.this,view);
    }

}
