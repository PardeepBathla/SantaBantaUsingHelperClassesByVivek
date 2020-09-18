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

import com.app.santabanta.Events.Events;
import com.app.santabanta.Helper.FragmentMemesHelper;
import com.app.santabanta.R;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.MemesExoPlayerRecyclerView;
import com.app.santabanta.Utils.Utils;
import com.app.santabanta.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.nio.file.Path;

import butterknife.ButterKnife;

import static com.app.santabanta.Utils.GlobalConstants.INTENT_PARAMS.SHOW_MEMES_FRAGMENT;

public class FragmentMemes extends BaseFragment {
    public  boolean IS_FROM_MENU = false;
    public  String subcat_slug_name = "";
    private static String TAG = FragmentMemes.class.getSimpleName();
    private Activity mActivity;
    public FragmentMemesHelper mHelper;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String slug = intent.getStringExtra(GlobalConstants.INTENT_PARAMS.MEME_SLUG);
            enterSubCategoryMemes(true,slug);
        }
    };

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
        try {
            getActivity().registerReceiver(receiver,new IntentFilter(SHOW_MEMES_FRAGMENT));
        }catch (Exception e) {
            e.printStackTrace();
        }
        initFragment(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            getActivity().unregisterReceiver(receiver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onEvent(Events.MemesEvent memesEvent) {
        enterSubCategoryMemes(true,memesEvent.getSlug());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            new MemesExoPlayerRecyclerView(getActivity()).onPausePlayer();
        }catch (Exception e){
        }
    }

    private void initFragment(View view) {
        mHelper = new FragmentMemesHelper(getActivity(),FragmentMemes.this,view);
    }

    public void enterSubCategoryMemes(boolean isSubCategoryMeme, String slug) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(GlobalConstants.INTENT_PARAMS.IS_SUB_CATEGORY_MEMES, isSubCategoryMeme);
        bundle.putString(GlobalConstants.INTENT_PARAMS.MEME_SLUG, slug);
        FragmentMemes fragmentSms1 = FragmentMemes.newInstance(bundle);
        Utils.switchFragment(getChildFragmentManager().beginTransaction(), fragmentSms1, R.id.frameMemes);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            if (getArguments().containsKey(GlobalConstants.INTENT_PARAMS.IS_SUB_CATEGORY_MEMES)) {
                IS_FROM_MENU = getArguments().getBoolean(GlobalConstants.INTENT_PARAMS.IS_SUB_CATEGORY_MEMES);
                subcat_slug_name = getArguments().getString(GlobalConstants.INTENT_PARAMS.MEME_SLUG);
            }
    }
}
