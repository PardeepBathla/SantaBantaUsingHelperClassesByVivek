package com.app.santabanta.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Helper.FragmentSmsHelper;
import com.app.santabanta.R;
import com.app.santabanta.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSms extends BaseFragment {

    private static String TAG = FragmentSms.class.getSimpleName();
    @BindView(R.id.ivPrevious)
    public ImageView ivPrevious;
    @BindView(R.id.rvSubCategorySms)
    public RecyclerView rvSubCategorySms;
    @BindView(R.id.ivNext)
    public ImageView ivNext;
    @BindView(R.id.rlSubCategories)
    public RelativeLayout rlSubCategories;
    @BindView(R.id.recyclerSms)
    public RecyclerView recyclerSms;
    private Activity mActivity;
    private FragmentSmsHelper mHelper;

    public static FragmentSms newInstance(Bundle bundle) {
        FragmentSms myFragment = new FragmentSms();
        myFragment.setArguments(bundle);
        return myFragment;
    }


    @Override
    public View getFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_sms, parent, false);
        ButterKnife.bind(this, view);
        mHelper = new FragmentSmsHelper(mActivity,this);
        return view;
    }

}
