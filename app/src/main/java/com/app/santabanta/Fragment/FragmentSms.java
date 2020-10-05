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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.santabanta.Events.Events;
import com.app.santabanta.Helper.FragmentSmsHelper;
import com.app.santabanta.Modals.SmsFeaturedCategory;
import com.app.santabanta.R;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.Utils;
import com.app.santabanta.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.santabanta.Utils.GlobalConstants.INTENT_PARAMS.SHOW_SMS_FRAGMENT;

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
    @BindView(R.id.tvNoDataFound)
    public TextView tvNoDataFound;
    @BindView(R.id.btnTryAgain)
    public Button btnTryAgain;
    private Activity mActivity;
    private FragmentSmsHelper mHelper;

    public static FragmentSms newInstance(Bundle bundle) {
        FragmentSms myFragment = new FragmentSms();
        myFragment.setArguments(bundle);
        return myFragment;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String slug = intent.getStringExtra(GlobalConstants.INTENT_PARAMS.SMS_SLUG);
            String category = intent.getStringExtra(GlobalConstants.INTENT_PARAMS.SMS_CATEGORY);

            enterSubCategorySms(true,slug,category);
        }
    };

    @Override
    public View getFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_sms, parent, false);
        ButterKnife.bind(this, view);
        try {
            getActivity().registerReceiver(receiver, new IntentFilter(SHOW_SMS_FRAGMENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mHelper = new FragmentSmsHelper(mActivity,this);
        return view;
    }

    @Subscribe
    public void onEvent(Events.SMSEvent smsEvent) {
        enterSubCategorySms(true,smsEvent.getSlug(),smsEvent.getCategory());
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            if (getArguments().containsKey(GlobalConstants.INTENT_PARAMS.IS_SUB_CATEGORY_SMS)) {
                IS_SUB_CATEGORY = getArguments().getBoolean(GlobalConstants.INTENT_PARAMS.IS_SUB_CATEGORY_SMS);
                subCatSlug = getArguments().getString(GlobalConstants.INTENT_PARAMS.SMS_SLUG);
                if (getArguments().containsKey(GlobalConstants.INTENT_PARAMS.SMS_CATEGORY))
                    selectedCategory = getArguments().getString(GlobalConstants.INTENT_PARAMS.SMS_CATEGORY);
            }
    }


    public void enterSubCategorySms(boolean isSubCategorySms, String slug, String category) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(GlobalConstants.INTENT_PARAMS.IS_SUB_CATEGORY_SMS, isSubCategorySms);
        bundle.putString(GlobalConstants.INTENT_PARAMS.SMS_SLUG, slug);
        bundle.putString(GlobalConstants.INTENT_PARAMS.SMS_CATEGORY,category);
        FragmentSms fragmentSms1 = FragmentSms.newInstance(bundle);
        Utils.switchFragment(getChildFragmentManager().beginTransaction(), fragmentSms1, R.id.frameSms);
    }


    public void enterSubCategorySms(boolean isSubCategorySms, String slug) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(GlobalConstants.INTENT_PARAMS.IS_SUB_CATEGORY_SMS, isSubCategorySms);
        bundle.putString(GlobalConstants.INTENT_PARAMS.SMS_SLUG, slug);
        FragmentSms fragmentSms1 = FragmentSms.newInstance(bundle);
        Utils.switchFragment(getChildFragmentManager().beginTransaction(), fragmentSms1, R.id.frameSms);
    }


}
