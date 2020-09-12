package com.app.santabanta.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.santabanta.Helper.FragmentHomeHelper;
import com.app.santabanta.R;
import com.app.santabanta.Utils.HomeMemesExoPlayerRecyclerview;
import com.app.santabanta.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHome extends BaseFragment {

    private static String TAG = FragmentHome.class.getSimpleName();
    @BindView(R.id.ivPrevious)
    public ImageView ivPrevious;
    @BindView(R.id.rvSubCategory)
    public RecyclerView rvSubCategory;
    @BindView(R.id.ivNext)
    public ImageView ivNext;
    @BindView(R.id.rlSubCategories)
    public RelativeLayout rlSubCategories;
    @BindView(R.id.recyclerHome)
    public HomeMemesExoPlayerRecyclerview recyclerHome;
    @BindView(R.id.swipeRefreshHome)
    public SwipeRefreshLayout swipeRefreshLayout;
    private Activity mActivity;
    private FragmentHomeHelper mHelper;


    public static FragmentHome newInstance(Bundle bundle) {
        FragmentHome myFragment = new FragmentHome();
        myFragment.setArguments(bundle);
        return myFragment;
    }


    @Override
    public View getFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_home, parent, false);
        ButterKnife.bind(this, view);
        mHelper = new FragmentHomeHelper(mActivity,this);
        return view;
    }

}
