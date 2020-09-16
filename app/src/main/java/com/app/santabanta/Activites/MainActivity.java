package com.app.santabanta.Activites;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;

import com.app.santabanta.Fragment.NavFragment;
import com.app.santabanta.Helper.MainActivityHelper;
import com.app.santabanta.R;
import com.app.santabanta.Utils.CheckPermissions;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.LocaleHelper;
import com.app.santabanta.Utils.Utils;
import com.app.santabanta.base.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.santabanta.Utils.GlobalConstants.INTENT_PARAMS.NAVIGATE_FROM_HOME;
import static com.app.santabanta.Utils.GlobalConstants.INTENT_PARAMS.NAVIGATE_SLUG;
import static com.app.santabanta.Utils.GlobalConstants.INTENT_PARAMS.NAVIGATE_TYPE;

public class MainActivity extends BaseActivity {

    public SharedPreferences pref;
    public NavFragment navFragment;
    public MainActivityHelper mHelper;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.rvBottom)
    RelativeLayout rvBottom;
    @BindView(R.id.tv_selected_module)
    TextView tvTitle;
    BroadcastReceiver navigateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String navigateType = intent.getStringExtra(NAVIGATE_TYPE);
            String navigateSlug = intent.getStringExtra(NAVIGATE_SLUG);
            switch (navigateType) {
                case "sms":
                    navFragment.mPager.setCurrentItem(1);
                    mHelper.openSmsFragment(navigateSlug, "Veg");
                    break;

                case "jokes":
                    navFragment.mPager.setCurrentItem(2);
                    mHelper.openJokesFragment(navigateSlug);
                    break;

                case "memes":
                    navFragment.mPager.setCurrentItem(3);
                    mHelper.openMemesFragment(navigateSlug);
                    break;
            }
        }
    };

    public Object getSystemService() {

        return getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pref = Utils.getSharedPref(MainActivity.this);
        setThemePreference();
        LocaleHelper.onAttach(MainActivity.this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        CheckPermissions.isStoragePermissionGranted(MainActivity.this);
        registerReceiver(navigateReceiver, new IntentFilter(NAVIGATE_FROM_HOME));
        initActivity();
        if (savedInstanceState == null) {
            initScreen();
        } else {
            try {
                if (getSupportFragmentManager().getFragments().get(0) instanceof NavFragment)
                    navFragment = (NavFragment) getSupportFragmentManager().getFragments().get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public TextView getTitleView() {
        return tvTitle;
    }

    private void initActivity() {
        mHelper = new MainActivityHelper(MainActivity.this);
        pref = Utils.getSharedPref(MainActivity.this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(navigateReceiver);
    }

    private void initScreen() {

        navFragment = new NavFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, navFragment)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if (navFragment != null) {
            if (!navFragment.onBackPressed()) {
                if (mHelper.drawer.isDrawerOpen(GravityCompat.END))
                    mHelper.drawer.closeDrawer(GravityCompat.END);
                else {
                    if (!(navFragment.mPager.getCurrentItem() == 0)) {
                        navFragment.mPager.setCurrentItem(0);
                    } else {
                        showExitAlert();
                    }

                }
            }
        } else {
            super.onBackPressed();
        }
    }

    private void showExitAlert() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_18_plus, null);
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        TextView txt_dia = dialog.findViewById(R.id.txt_dia);
        Button btn_yes = dialog.findViewById(R.id.btn_yes);
        Button btn_no = dialog.findViewById(R.id.btn_no);

        btn_yes.setText(getString(R.string.ok));
        btn_no.setText(getString(R.string.cancel));
        txt_dia.setText(getString(R.string.are_you_sure_you_want_to_exit));
        btn_no.setOnClickListener(view13 -> {
            dialog.dismiss();
        });
        btn_yes.setOnClickListener(view1 -> {
            dialog.dismiss();
            finish();
        });
        dialog.show();
    }


    public void showCurrentPage(int pos) {
        if (navFragment != null)
            navFragment.setCurrentPage(pos);
    }

    private void setThemePreference() {
        if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
            setTheme(R.style.AppThemeLight);
        } else {
            setTheme(R.style.AppThemeDark);
        }

    }


    public void switchTheme() {
        if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
            onCheckedChanged(false);
        } else {
            onCheckedChanged(true);
        }
        finish();
        startActivity(getIntent());
    }

    public void onCheckedChanged(boolean checked) {

        SharedPreferences.Editor editor = pref.edit();

        if (checked) {
            editor.putBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, checked); // Storing boolean - true/false
        } else {
            editor.putBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, checked); // Storing boolean - true/false
        }
        editor.apply();

    }

    public void vibrate() {

        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(100);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CheckPermissions.REQUEST_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.dialog_18_plus, null);
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(view);
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(true);

                    TextView txt_dia = dialog.findViewById(R.id.txt_dia);
                    Button btn_yes = dialog.findViewById(R.id.btn_yes);
                    Button btn_no = dialog.findViewById(R.id.btn_no);

                    btn_yes.setText(getString(R.string.ok));
                    btn_no.setText(getString(R.string.cancel));
                    txt_dia.setText(getString(R.string.permission_alert));
                    btn_no.setOnClickListener(view13 -> {
                        dialog.dismiss();
                        finish();
                    });
                    btn_yes.setOnClickListener(view1 -> {
                        CheckPermissions.isStoragePermissionGranted(MainActivity.this);
                        dialog.dismiss();
                    });
                    btn_no.setOnClickListener(view12 -> dialog.dismiss());
                    dialog.show();
                }
            }
        }
    }
}