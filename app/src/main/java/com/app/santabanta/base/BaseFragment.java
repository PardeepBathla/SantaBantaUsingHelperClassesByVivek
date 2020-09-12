package com.app.santabanta.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.santabanta.Callbacks.OnBackPressListener;
import com.app.santabanta.Utils.BackPressImpl;


public abstract class BaseFragment extends Fragment implements OnBackPressListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        View view = getFragmentView(inflater, parent, savedInstanseState);

        return view;
    }

    public abstract View getFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    @Override
    public boolean onBackPressed() {
        return new BackPressImpl(this).onBackPressed();
    }


}
