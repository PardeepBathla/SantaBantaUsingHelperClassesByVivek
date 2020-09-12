package com.app.santabanta.Adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.santabanta.Fragment.FragmentHome;
import com.app.santabanta.Fragment.FragmentJokes;
import com.app.santabanta.Fragment.FragmentMemes;
import com.app.santabanta.Fragment.FragmentSms;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;
    Activity mActivity;
    SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public ViewPagerAdapter(final Resources resources, FragmentManager fm) {
        super(fm);
        this.resources = resources;
    }

    @Override
    public Fragment getItem(int position) {
        final Fragment result;
        switch (position) {
            case 0:
                result = new FragmentHome();
                break;
            case 1:
                result = new FragmentSms();
                break;
            case 2:
                result = new FragmentJokes();
                break;
            case 3:
                result = new FragmentMemes();
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
