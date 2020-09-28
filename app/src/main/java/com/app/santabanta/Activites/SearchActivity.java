package com.app.santabanta.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.app.santabanta.Helper.SearchActivityHelper;
import com.app.santabanta.R;
import com.app.santabanta.base.BaseActivity;

public class SearchActivity extends BaseActivity {

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.mActivity = this;
        new SearchActivityHelper(mActivity);
    }
}