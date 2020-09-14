package com.app.santabanta.Fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.santabanta.Helper.FragmentJokesHelper;
import com.app.santabanta.R;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.Utils;
import com.app.santabanta.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.santabanta.Utils.GlobalConstants.INTENT_PARAMS.SHOW_JOKES_FRAGMENT;

public class FragmentJokes extends BaseFragment {

    private static String TAG = FragmentJokes.class.getSimpleName();

    public String slugName = "";
    public boolean IS_SUB_CAT = false;
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
    @BindView(R.id.tvNoDataFound)
    public TextView tvNoDataFound;
    private Activity mActivity;
    private FragmentJokesHelper mHelper;

    public static FragmentJokes newInstance(Bundle bundle) {
        FragmentJokes myFragment = new FragmentJokes();
        myFragment.setArguments(bundle);
        return myFragment;
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String slug = intent.getStringExtra(GlobalConstants.INTENT_PARAMS.JOKE_SLUG);
            enterSubCategoryJoke(true,slug);
        }
    };


    @Override
    public View getFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_jokes, parent, false);
        ButterKnife.bind(this, view);
        try {
            getActivity().registerReceiver(receiver, new IntentFilter(SHOW_JOKES_FRAGMENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mHelper = new FragmentJokesHelper(mActivity, this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            getActivity().unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            if (getArguments().containsKey(GlobalConstants.INTENT_PARAMS.IS_SUB_CATEGORY_JOKES)) {
                IS_SUB_CAT = getArguments().getBoolean(GlobalConstants.INTENT_PARAMS.IS_SUB_CATEGORY_JOKES);
                slugName = getArguments().getString(GlobalConstants.INTENT_PARAMS.JOKE_SLUG);

            }
    }

    public void enterSubCategoryJoke(boolean isSubCategoryJoke, String slug) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(GlobalConstants.INTENT_PARAMS.IS_SUB_CATEGORY_JOKES, isSubCategoryJoke);
        bundle.putString(GlobalConstants.INTENT_PARAMS.JOKE_SLUG, slug);
        FragmentJokes fragmentSms1 = FragmentJokes.newInstance(bundle);
        Utils.switchFragment(getChildFragmentManager().beginTransaction(), fragmentSms1, R.id.frameJokes);
    }


}
