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
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Fragment.NavFragment;
import com.app.santabanta.Helper.MainActivityHelper;
import com.app.santabanta.R;
import com.app.santabanta.Utils.CheckPermissions;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.LocaleHelper;
import com.app.santabanta.Utils.ResUtils;
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
    public FrameLayout container;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.rvBottom)
    RelativeLayout rvBottom;
    @BindView(R.id.tv_selected_module)
    TextView tvTitle;
    @BindView(R.id.et_search)
    public EditText etSearch;
    @BindView(R.id.flSearch)
    public FrameLayout flSearch;
    @BindView(R.id.pbSearch)
    public ProgressBar pbSearch;
    @BindView(R.id.tvNoDataFoundSearch)
    public TextView tvNoDataFoundSearch;
    @BindView(R.id.rvSearch)
    public RecyclerView rvSearch;
    private BroadcastReceiver navigateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String navigateType = intent.getStringExtra(NAVIGATE_TYPE);
            String navigateSlug = intent.getStringExtra(NAVIGATE_SLUG);
            if (navigateType != null) {
                switch (navigateType) {
                    case "sms":
                        navFragment.getViewPager().setCurrentItem(1);
                        mHelper.openSmsFragment(navigateSlug, "Veg");
                        break;

                    case "jokes":
                        navFragment.getViewPager().setCurrentItem(2);
                        mHelper.openJokesFragment(navigateSlug);
                        break;

                    case "memes":
                        navFragment.getViewPager().setCurrentItem(3);
                        mHelper.openMemesFragment(navigateSlug);
                        break;
                }
            }
        }
    };

    private long delay = 700; // 1 seconds after user stops typing
    private long last_text_edit = 0;
    private Handler handler = new Handler();


    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                String searchText = etSearch.getText().toString().trim();
                if (searchText.length() > 0) {
                    flSearch.setVisibility(View.VISIBLE);
                    container.setVisibility(View.GONE);
                    mHelper.searchText(searchText);
                    tvNoDataFoundSearch.setText(ResUtils.getString(R.string.loading));
                }else {
                    flSearch.setVisibility(View.GONE);
                    container.setVisibility(View.VISIBLE);
                }
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

        if (getIntent().hasExtra("change"))
            setThemePreference();
        else {
            setTheme(R.style.AppThemeLight);
            onCheckedChanged(true);
        }

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

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                } else {
                    flSearch.setVisibility(View.GONE);
                    container.setVisibility(View.VISIBLE);
                }


            }
        });

        etSearch.setTextColor(pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false) ? ResUtils.getColor(R.color.off_black) : ResUtils.getColor(R.color.off_black));

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
                    if (!(navFragment.getViewPager().getCurrentItem() == 0)) {
                        navFragment.getViewPager().setCurrentItem(0);
                    } else {
                        if (flSearch.getVisibility() == View.VISIBLE){
                            flSearch.setVisibility(View.GONE);
                            etSearch.setText("");
                            container.setVisibility(View.VISIBLE);
                        }else{
                            showExitAlert();
                        }

                    }

                }
            }
        } else {
            super.onBackPressed();
        }
    }

    private void showExitAlert() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (inflater != null) {
            ViewGroup viewGroup = null;
            view = inflater.inflate(R.layout.dialog_18_plus, null);
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
        startActivity(getIntent().putExtra("change",true));
        finish();

    }

    public void onCheckedChanged(boolean checked) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, checked);
        editor.apply();
    }

    public void vibrate() {
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (v != null) {
                v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        } else {
            if (v != null) {
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