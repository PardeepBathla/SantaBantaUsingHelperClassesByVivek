package com.app.santabanta.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.santabanta.Helper.FragmentSmsHelper;
import com.app.santabanta.R;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSms extends BaseFragment {

    private static String TAG = FragmentSms.class.getSimpleName();

    public boolean IS_SUB_CATEGORY = false;
    public String subCatSlug = "";
    public String selectedCategory = "Veg";

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
    @BindView(R.id.swipeRefreshSms)
    public SwipeRefreshLayout swipeRefreshSms;
    @BindView(R.id.frameSms)
    public FrameLayout frameSms;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            if (getArguments().containsKey(GlobalConstants.INTENT_PARAMS.IS_SUB_CATEGORY_SMS)) {
                IS_SUB_CATEGORY = getArguments().getBoolean(GlobalConstants.INTENT_PARAMS.IS_SUB_CATEGORY_SMS);
                subCatSlug = getArguments().getString(GlobalConstants.INTENT_PARAMS.SMS_SLUG);

            }
    }


}
