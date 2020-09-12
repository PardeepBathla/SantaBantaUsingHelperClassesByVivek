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

import com.app.santabanta.Helper.FragmentJokesHelper;
import com.app.santabanta.R;
import com.app.santabanta.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentJokes extends BaseFragment {

    private static String TAG = FragmentJokes.class.getSimpleName();
    @BindView(R.id.ivPrevious)
    public ImageView ivPrevious;
    @BindView(R.id.rvSubCategoryJokes)
    public RecyclerView rvSubCategoryJokes;
    @BindView(R.id.ivNext)
    public ImageView ivNext;
    @BindView(R.id.rlSubCategories)
    public RelativeLayout rlSubCategories;
    @BindView(R.id.recyclerJokes)
    public RecyclerView recyclerJokes;
    @BindView(R.id.swipeRefreshJokes)
    public SwipeRefreshLayout swipeRefreshJokes;
    private Activity mActivity;
    private FragmentJokesHelper mHelper;

    public static FragmentJokes newInstance(Bundle bundle) {
        FragmentJokes myFragment = new FragmentJokes();
        myFragment.setArguments(bundle);
        return myFragment;
    }


    @Override
    public View getFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_jokes, parent, false);
        ButterKnife.bind(this, view);
        mHelper = new FragmentJokesHelper(mActivity, this);
        return view;
    }

}
