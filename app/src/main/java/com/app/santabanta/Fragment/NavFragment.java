package com.app.santabanta.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.ViewPager;

import com.app.santabanta.Callbacks.OnBackPressListener;
import com.app.santabanta.Helper.NavFragmentHelper;
import com.app.santabanta.R;
import com.app.santabanta.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavFragment extends BaseFragment {

    @BindView(R.id.vp_pages)
    public ViewPager mPager;
    @BindView(R.id.tabs)
    public TabLayout mTabLayout;
    private Activity mActivity;
    private NavFragmentHelper mHelper;

    @Override
    public View getFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_nav, parent, false);
        ButterKnife.bind(this, view);
        mHelper = new NavFragmentHelper(this, mPager, mTabLayout,mActivity);
        return view;
    }

    public boolean onBackPressed() {

        OnBackPressListener currentFragment = (OnBackPressListener) mHelper.getAdapter().getRegisteredFragment(mPager.getCurrentItem());
        if (currentFragment != null) {
            return currentFragment.onBackPressed();
        }
        return false;
    }

    public ViewPager getViewPager(){
        return mPager;
    }

    public void setCurrentPage(int pos){
        if (mPager!=null)
            mPager.setCurrentItem(pos);
    }
}
